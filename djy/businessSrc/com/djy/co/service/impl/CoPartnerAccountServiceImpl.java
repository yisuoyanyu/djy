package com.djy.co.service.impl;

import org.springframework.stereotype.Service;

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAccount;
import com.djy.co.model.CoSettleOrder;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoPartnerAccountService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.enumtype.FinChannel;
import com.frame.base.service.impl.BaseServiceImpl;


@Service("coPartnerAccountService")
public class CoPartnerAccountServiceImpl extends BaseServiceImpl<CoPartnerAccount> implements CoPartnerAccountService {
	
	
	@Override
	public Double updateBySysDepositOrder(CoSysDepositOrder coSysDepositOrder) {
		
		CoPartner coPartner = coSysDepositOrder.getCoPartner();
		CoPartnerAccount coPartnerAccount = coPartner.getCoPartnerAccount();
		
		Double depositOrderAmount = coSysDepositOrder.getAmount();
		
		double sysDeposit = 0d;
		CoSysDepositLog coSysDepositLog = coSysDepositOrder.getCoSysDepositLog();
		if ( coSysDepositOrder.getFinTransfer() != null && coSysDepositOrder.getFinTransfer().getChannel() == FinChannel.wxPub.getId() 
				&& coSysDepositLog != null ) {			// 微信公众号支付的企业付款不存在监听，所以直接以消费订单预存额日志对应预存额来运算
			sysDeposit = coSysDepositLog.getSysDeposit();
		} else if ( coPartnerAccount.getSysDeposit() != null ) {
			sysDeposit = coPartnerAccount.getSysDeposit();
		}
		
		String hql = "UPDATE " + CoPartnerAccount.class.getName() + " account SET "
				+ " account.sysDeposit=account.sysDeposit+" + depositOrderAmount
				+ ", account.totalSysDeposit=account.totalSysDeposit+" + depositOrderAmount
				+ " WHERE account.id=" + coPartnerAccount.getId();
		this.executeHql(hql);
		
		sysDeposit += depositOrderAmount;
		
		return sysDeposit;
	}
	
	
	@Override
	public Double updateByConsumeOrder(ConsumeOrder consumeOrder) {
		
		CoPartner coPartner = consumeOrder.getCoPartner();
		CoPartnerAccount coPartnerAccount = coPartner.getCoPartnerAccount();
		
		Double consumeOrderAmount = consumeOrder.getConsumeAmount();
		
		double sysDeposit = 0d;
		if (coPartnerAccount.getSysDeposit() != null) {
			sysDeposit = coPartnerAccount.getSysDeposit();
		}
		
		String hql = "UPDATE " + CoPartnerAccount.class.getName() + " account SET "
				+ " account.totalCstmConsume=account.totalCstmConsume+" + consumeOrderAmount
				+ ", account.totalCstmPay=account.totalCstmPay+" + consumeOrder.getPayAmount();
		
		if ( coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId() ) {
			hql += ", account.sysDeposit=account.sysDeposit-" + consumeOrderAmount;
		}
		
		hql += " WHERE account.id=" + coPartnerAccount.getId();
		
		this.executeHql(hql);
		
		sysDeposit -= consumeOrderAmount;
		
		return sysDeposit;
	}
	
	
	@Override
	public void updateByCoSettleOrder(CoSettleOrder coSettleOrder) {
		
		if ( coSettleOrder.getConsumeOrder() != null ) {
			
			CoPartner coPartner = coSettleOrder.getCoPartner();
			CoPartnerAccount coPartnerAccount = coPartner.getCoPartnerAccount();
			
			String hql = "UPDATE " + CoPartnerAccount.class.getName() + " account SET "
					+ " account.totalCstmSettle=account.totalCstmSettle+" + coSettleOrder.getAmount();
			
			hql += " WHERE account.id=" + coPartnerAccount.getId();
			
			this.executeHql(hql);
			
		}
		
	}
	
	
}
