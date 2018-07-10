package com.djy.co.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSysDepositLogDao;
import com.djy.co.enumtype.CoSysDepositLogIncomeExpense;
import com.djy.co.enumtype.CoSysDepositLogType;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoPartnerAccountService;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.co.service.CoSysDepositOrderService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatTmplMsgService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.NumberUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coSysDepositLogService")
public class CoSysDepositLogServiceImpl extends BaseServiceImpl<CoSysDepositLog> implements CoSysDepositLogService{
	
	private static Logger logger = LoggerFactory.getLogger( CoSysDepositLogServiceImpl.class );
	
	@Autowired
	private CoSysDepositLogDao coSysDepositLogDao;
	
	@Autowired
	private CoPartnerAccountService coPartnerAccountService;
	
	@Autowired
	private CoSysDepositOrderService coSysDepositOrderService;
	
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	
	
	@Override
	public List<CoSysDepositLog> search(PagingBean pb, Map<String, Object> param) {
		
		return this.coSysDepositLogDao.search(pb, param);
		
	}
	
	
	@Override
	public Double search(Map<String, Object> param) {
		
		return this.coSysDepositLogDao.search(param);
		
	}
	
	
	@Override
	public List<CoSysDepositLog> searchByCoPartnerId( Integer coPartnerId ,Integer page) {
		
		return this.coSysDepositLogDao.searchByCoPartnerId(coPartnerId, page);
	}
	
	
	@Override
	public void logConsumeOrder( ConsumeOrder consumeOrder ) {
		
		CoPartner coPartner = consumeOrder.getCoPartner();
		
		// 更新平台预存额
		Double sysDeposit = coPartnerAccountService.updateByConsumeOrder( consumeOrder );
		
		// 记录预存额日志
		CoSysDepositLog log = new CoSysDepositLog();
		log.setCoPartner( coPartner );
		log.setType( CoSysDepositLogType.consume.getId() );
		log.setIncomeExpense( CoSysDepositLogIncomeExpense.expense.getId() );
		log.setAmount( consumeOrder.getConsumeAmount() );
		log.setSysDeposit( sysDeposit );
		log.setInsertTime( new Date() );
		log.setConsumeOrder( consumeOrder );
		this.save( log );
		
		// 模板消息通知 合作商家 用户消费订单付款成功
		try {
			msgConsumeToPartner( log );
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		// 预存额 小于 平台最低预存限额时，新建平台预存订单
		if ( sysDeposit < coPartner.getMinSysDeposit() ) {
			coSysDepositOrderService.createByCoSysDepositLog( log );
		}
		
	}
	
	
	@Override
	public void logCoSysDeposit( CoSysDepositOrder coSysDepositOrder ) {
		
		CoPartner coPartner = coSysDepositOrder.getCoPartner();
		
		// 更新平台预存额
		Double sysDeposit = coPartnerAccountService.updateBySysDepositOrder( coSysDepositOrder );
		
		// 记录预存额日志
		CoSysDepositLog log = new CoSysDepositLog();
		log.setCoPartner( coPartner );
		log.setType( CoSysDepositLogType.deposit.getId() );
		log.setIncomeExpense( CoSysDepositLogIncomeExpense.income.getId() );
		log.setAmount( coSysDepositOrder.getAmount() );
		log.setSysDeposit( sysDeposit );
		log.setInsertTime( new Date() );
		log.setCoSysDepositOrder( coSysDepositOrder );
		this.save( log );
		
		
		// 模板消息通知 合作商家 平台预存成功
		try {
			msgDepositToPartner( log );
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Override
	public double getCoSysDepositLogStartSysDeposit(Map<String, Object> param) {
		return this.coSysDepositLogDao.getCoSysDepositLogStartSysDeposit(param);
	}
	

	@Override
	public double getCoSysDepositLogEndSysDeposit(Map<String, Object> param) {
		return this.coSysDepositLogDao.getCoSysDepositLogEndSysDeposit(param);
	}


	@Override
	public List<CoSysDepositLog> searchAllCoSysDepositLogs(Map<String, Object> param) {
		return this.coSysDepositLogDao.searchAllCoSysDepositLogs(param);
	}
	
	
	//----------------------------------------
	
	/**
	 * 模板消息通知 合作商家 用户消费订单付款成功
	 * 
	 * @param log
	 */
	private void msgConsumeToPartner(CoSysDepositLog log) {
		
		CoPartner coPartner = log.getCoPartner();
		ConsumeOrder consumeOrder = log.getConsumeOrder(); 
		
		WechatUser toUser = coPartner.getUser().getWechatUser();
		
		String tmplCode = "coPartnerReceiptSuccess";
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有新的收款信息！");
		
		// 门店名称
		data.put("keyword1", coPartner.getName());
		
		// 订单编号
		data.put("keyword2", consumeOrder.getNo());
		
		// 交易金额、扣减预存、当前余额
		String amountStr = "¥ " + NumberUtil.formatPrice( consumeOrder.getConsumeAmount() ) + " 元";
		amountStr += "\n扣减预存：¥ " + NumberUtil.formatPrice( consumeOrder.getConsumeAmount() ) + " 元";
		amountStr += "\n预存余额：¥ " + NumberUtil.formatPrice( log.getSysDeposit() ) + " 元";
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
	
	
	/**
	 * 模板消息通知 合作商家 平台预存成功
	 * 
	 * @param log
	 */
	private void msgDepositToPartner(CoSysDepositLog log) {
		
		CoPartner coPartner = log.getCoPartner();
		CoSysDepositOrder coSysDepositOrder = log.getCoSysDepositOrder();
		
		WechatUser toUser = coPartner.getUser().getWechatUser();
		
		String tmplCode = "coSysDepositSuccess";
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有新的平台预存信息！");
		
		// 充值时间
		String timeStr = DateUtil.formatDate(coSysDepositOrder.getInsertTime(), "yyyy-MM-dd HH:mm:ss");
		data.put("keyword1", timeStr);
		
		// 充值金额、实收金额
		String amountStr = "¥ " + NumberUtil.formatPrice( coSysDepositOrder.getAmount() ) + " 元";
		amountStr += "\n实收金额：¥ " + NumberUtil.formatPrice( coSysDepositOrder.getPayAmount() ) + " 元";
		data.put("keyword2", amountStr);
		
		// 充值方式
		if ( coSysDepositOrder.getFinTransfer() != null ) {
			data.put("keyword3", coSysDepositOrder.getFinTransfer().getChannelName());
		} else {
			data.put("keyword3", "手工转账");
		}
		
		// 当前余额
		data.put("keyword4", "¥ " + NumberUtil.formatPrice( log.getSysDeposit() ) + " 元" );
		
		data.put("remark", "感谢您的使用！");
		
		try {
			wechatTmplMsgService.sendTmplMsg(toUser, tmplCode, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
}
