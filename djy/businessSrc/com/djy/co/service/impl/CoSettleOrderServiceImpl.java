package com.djy.co.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSettleOrderDao;
import com.djy.co.enumtype.CoSettleOrderStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSettleOrder;
import com.djy.co.service.CoPartnerAccountService;
import com.djy.co.service.CoSettleOrderService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.enumtype.FinTransferStatus;
import com.djy.fin.model.FinTransfer;
import com.djy.fin.service.FinTransferService;
import com.djy.utils.enumtype.SnPrefix;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatTmplMsgService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.NumberUtil;
import com.frame.base.utils.SnGenerator;
import com.frame.base.web.vo.PagingBean;

@Service("coSettleOrderService")
public class CoSettleOrderServiceImpl extends BaseServiceImpl<CoSettleOrder> implements CoSettleOrderService{
	
	private static Logger logger = LoggerFactory.getLogger( CoSettleOrderServiceImpl.class );
	
	
	@Autowired
	private CoSettleOrderDao coSettleOrderDao;
	
	@Autowired
	private FinTransferService finTransferService;
	
	@Autowired
	private CoPartnerAccountService coPartnerAccountService;
	
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	
	
	@Override
	public List<CoSettleOrder> search(PagingBean pb, Map<String, Object> param) {
		
		return coSettleOrderDao.search(pb,param);
		
	}

	
	@Override
	public CoSettleOrder createByConsumeOrder(ConsumeOrder consumeOrder) {
		
		CoPartner coPartner = consumeOrder.getCoPartner();
		
		// 结算金额
		double settleAmount = consumeOrder.getConsumeAmount() * coPartner.getSysSettlePercent() / 100;
		
		CoSettleOrder order = new CoSettleOrder();
		order.setNo( SnGenerator.generate( SnPrefix.coSettleOrder.getValue() ) );
		order.setCoPartner( consumeOrder.getCoPartner() );
		order.setAmount( settleAmount );
		order.setConsumeOrder( consumeOrder );
		order.setStatus( CoSettleOrderStatus.unpaid.getId() );
		order.setInsertTime( new Date() );
		this.save( order );
		
		// 企业付款
		FinTransfer finTransfer = finTransferService.createByCoSettle( order );
		order.setFinTransfer( finTransfer );
		if ( finTransfer.getStatus() == FinTransferStatus.paying.getId() ) {
			
			order.setStatus( CoSettleOrderStatus.paying.getId() );
			
		} else if ( finTransfer.getStatus() == FinTransferStatus.paySuccess.getId() ) {
			
			order.setStatus( CoSettleOrderStatus.paySuccess.getId() );
			
		} else if ( finTransfer.getStatus() == FinTransferStatus.payFail.getId() ) {
			
			order.setStatus( CoSettleOrderStatus.payFail.getId() );
			
		}
		
		this.update( order );
		
		if ( order.getStatus() == CoSettleOrderStatus.paySuccess.getId() ) {
			coPartnerAccountService.updateByCoSettleOrder( order );
		}
		
		// 模板消息通知 合作商家 用户消费订单付款成功
		try {
			msgConsumeToPartner( order );
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return order;
	}


	@Override
	public double getCoSettleOrderAmount(Map<String, Object> param) {
		return coSettleOrderDao.getCoSettleOrderAmount(param);
	}
	
	
	//--------------------------------------------
	
	/**
	 * 模板消息通知 合作商家 用户消费订单付款成功
	 * 
	 * @param order
	 */
	private void msgConsumeToPartner(CoSettleOrder order) {
		
		CoPartner coPartner = order.getCoPartner();
		ConsumeOrder consumeOrder = order.getConsumeOrder();
		
		WechatUser toUser = coPartner.getUser().getWechatUser();
		
		String tmplCode = "coPartnerReceiptSuccess";
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有新的收款信息！");
		
		// 门店名称
		data.put("keyword1", coPartner.getName());
		
		// 订单编号
		data.put("keyword2", consumeOrder.getNo());
		
		// 交易金额、实收金额
		String amountStr = "¥ " + NumberUtil.formatPrice( consumeOrder.getConsumeAmount() ) + " 元";
		amountStr += "\n实收金额：¥ " + NumberUtil.formatPrice( order.getAmount() ) + " 元";
		data.put("keyword3", amountStr);
		
		// 付款用户
		String nickname = consumeOrder.getUser().getWechatUser().getNickname();
		data.put("keyword4", nickname);
		
		// 支付方式
		data.put("keyword5", consumeOrder.getFinCharge().getChannelName());
		
		data.put("remark", "感谢您的使用！");
		
		try {
			wechatTmplMsgService.sendTmplMsg(toUser, tmplCode, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}


	@Override
	public double getInvitedCoSettleAmountByCoParnerId(Map<String, Object> param) {
		return coSettleOrderDao.getInvitedCoSettleAmountByCoParnerId(param);
	}
	
	
}
