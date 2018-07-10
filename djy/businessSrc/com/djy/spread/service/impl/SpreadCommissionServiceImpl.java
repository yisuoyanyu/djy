package com.djy.spread.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.djy.spread.dao.SpreadCommissionDao;
import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.enumtype.FinTransferStatus;
import com.djy.fin.model.FinTransfer;
import com.djy.fin.service.FinTransferService;
import com.djy.spread.enumtype.SpreadCommissionStatus;
import com.djy.spread.model.SpreadCommission;
import com.djy.spread.service.SpreadCommissionService;
import com.djy.user.model.User;
import com.djy.utils.enumtype.SnPrefix;
import com.djy.wechat.service.WechatTmplMsgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.utils.NumberUtil;
import com.frame.base.utils.SnGenerator;

@Service("spreadCommissionService")
public class SpreadCommissionServiceImpl extends BaseServiceImpl<SpreadCommission> implements SpreadCommissionService{
	
	
	private static Logger logger = LoggerFactory.getLogger( SpreadCommissionServiceImpl.class );
	@Autowired
	private SpreadCommissionDao spreadCommissionDao;
	@Autowired
	private FinTransferService finTransferService;
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	@Override
	public SpreadCommission getCommByUser(User user) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("consumeOrder.user", user);
		return getByFilter(hqlFilter);
	}
	
	

	@Override
	public List<SpreadCommission> search(PagingBean pb, Map<String, Object> param) {
		return spreadCommissionDao.search(pb,param);
	}


	@Override
	public double countCommissionTotalAmount(Map<String, Object> param) {
		return this.spreadCommissionDao.countCommissionTotalAmount(param);
	}

	@Override
	public List<SpreadCommission> getCommByUser(User user, Integer page) {
		
		return spreadCommissionDao.getCommByUser(user, page);
		
	}

	@Override
	public long getTotalNumByUser(User user) {
		
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("spreadPromoter.user", user);
		return countByFilter(hqlFilter);
		
	}

	@Override
	public void dealFirstPaySuccess(ConsumeOrder consumeOrder) {
		// TODO Auto-generated method stub
		SpreadCommission spreadCommission = new SpreadCommission();
		// 首次推广金额
		double spreadAmount = consumeOrder.getUser().getSponsor().getSpreadPromoter().getSpreadConsumeFirstComm();
		
		spreadCommission.setAmount(spreadAmount);
		spreadCommission.setConsumeOrder(consumeOrder);
		spreadCommission.setInsertTime(new Date());
		spreadCommission.setNo(SnGenerator.generate( SnPrefix.spreadCommison.getValue() ) );
		spreadCommission.setSpreadPromoter(consumeOrder.getUser().getSponsor().getSpreadPromoter());
		spreadCommission.setStatus(SpreadCommissionStatus.unpaid.getId());//还未发起付款，处于付款中的状态
		
		try {
			this.save(spreadCommission);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		// 企业付款
		FinTransfer finTransfer = finTransferService.createBySpreadCommison(spreadCommission);
		spreadCommission.setFinTransfer( finTransfer );
		if ( finTransfer.getStatus() == FinTransferStatus.paying.getId() ) {
			
			spreadCommission.setStatus( SpreadCommissionStatus.paying.getId() );
			
		} else if ( finTransfer.getStatus() == FinTransferStatus.paySuccess.getId() ) {
			
			spreadCommission.setStatus( SpreadCommissionStatus.paySuccess.getId() );
			
		} else if ( finTransfer.getStatus() == FinTransferStatus.payFail.getId() ) {
			
			spreadCommission.setStatus( SpreadCommissionStatus.payFail.getId() );
			
		}
		
		this.update( spreadCommission );
		
		
		// 模板消息通知 推广会员，推广佣金已经到账
		try {
			msgCommisonToSpreader(spreadCommission);
		} catch(Exception e) {
			//logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 模板消息通知 推广职员  获取奖励成功
	 * 
	 * @param consumeOrder
	 */
	private void msgCommisonToSpreader(SpreadCommission spreadCommission) {
		
		String tmplCode = "coEmplReceiptSuccess";
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有新的奖励收款信息！");
		
		// 门店名称
		data.put("keyword1", spreadCommission.getConsumeOrder().getCoPartner().getName());
		
		// 订单编号
		data.put("keyword2", spreadCommission.getNo());
		
		// 交易金额
		String amountStr = "¥ " + NumberUtil.formatPrice( spreadCommission.getConsumeOrder().getConsumeAmount() ) + " 元";
		amountStr += "\n奖励金额：¥ " + NumberUtil.formatPrice( spreadCommission.getAmount() ) + " 元";
		data.put("keyword3", amountStr);
		
		// 付款用户
		String nickname = spreadCommission.getConsumeOrder().getUser().getWechatUser().getNickname();
		data.put("keyword4", nickname);
		
		// 支付方式
		data.put("keyword5", spreadCommission.getConsumeOrder().getFinCharge().getChannelName());
		
		data.put("remark", "感谢您的推广！");
			
		try {	
			wechatTmplMsgService.sendTmplMsg(spreadCommission.getSpreadPromoter().getUser().getWechatUser(), tmplCode, data);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	
}
