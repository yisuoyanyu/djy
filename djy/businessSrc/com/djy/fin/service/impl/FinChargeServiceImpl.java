package com.djy.fin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.fin.dao.FinChargeDao;
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.enumtype.FinChargeStatus;
import com.djy.fin.enumtype.FinChargeType;
import com.djy.fin.model.FinCharge;
import com.djy.fin.service.FinChargeService;
import com.djy.spread.service.SpreadCommissionService;
import com.djy.wxPay.model.WxPayCharge;
import com.djy.wxPay.service.WxPayChargeService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;



@Service("finChargeService")
public class FinChargeServiceImpl extends BaseServiceImpl<FinCharge> implements FinChargeService {
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private WxPayChargeService wxPayChargeService;
	
	@Autowired
	private FinChargeDao finChargeDao;
	
	@Autowired
	private SpreadCommissionService spreadCommissionService;
	
	@Override
	public FinCharge getByOrderNo(String orderNo) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("orderNo", orderNo);
		return getByFilter(hqlFilter);
	}
	
	//---------------------------------------------
	
	@Override
	public FinCharge createByConsumeOrder(ConsumeOrder consumeOrder, FinChannel finChannel, String payerIp) {
		FinCharge finCharge = new FinCharge();
		finCharge.setType( FinChargeType.consumeOrder.getId() );
		finCharge.setUser( consumeOrder.getUser() );
		finCharge.setChannel( finChannel.getId() );
		finCharge.setOrderNo( consumeOrder.getNo() );
		finCharge.setClientIp( payerIp );
		finCharge.setSubject( "在" + consumeOrder.getCoPartner().getName() + "消费" + consumeOrder.getConsumeAmount() + "元" );
		finCharge.setAmount( consumeOrder.getPayAmount() );
		finCharge.setStatus( FinChargeStatus.unpaid.getId() );
		finCharge.setInsertTime( new Date() );
		finCharge.setConsumeOrder( consumeOrder );
		this.save( finCharge );
		
		if ( finChannel.getId() == FinChannel.wxPub.getId() ) {
			WxPayCharge wxPayCharge = wxPayChargeService.createByFinCharge( finCharge );
			finCharge.setWxPayCharge( wxPayCharge );
			finCharge.setStatus( FinChargeStatus.paying.getId() );
			this.saveOrUpdate( finCharge );
		}
		
		return finCharge;
	}
	
	//---------------------------------------------
	
	@Override
	public void dealPaySuccess(FinCharge finCharge) {
		
		finCharge.setStatus( FinChargeStatus.paySuccess.getId() );
		finCharge.setPaid( true );
		finCharge.setTimePaid( new Date() );
		this.update( finCharge );
		
		if ( finCharge.getType() == FinChargeType.consumeOrder.getId() ) {	// 会员消费
			
			consumeOrderService.dealPaySuccess( finCharge.getConsumeOrder() );
			
		if (consumeOrderService.isFirstBuy(finCharge.getConsumeOrder().getUser())) {//是否为第一次购买信息
			
			// 从此处开始调用测试ACtion
			spreadCommissionService.dealFirstPaySuccess(finCharge.getConsumeOrder());
			
		}
		
		}
		
	}
	
	
	@Override
	public void dealPayFail(FinCharge finCharge) {
		
		finCharge.setStatus(FinChargeStatus.payFail.getId());
		this.update( finCharge );
		
		if ( finCharge.getType() == FinChargeType.consumeOrder.getId() ) {	// 会员消费
			
			consumeOrderService.dealPayFail( finCharge.getConsumeOrder() );
			
		}
		
	}
	
	
	@Override
	public void dealPayFinish(FinCharge finCharge) {
		
		if ( finCharge.getStatus() != FinChargeStatus.unpaid.getId() )
			return;
		
		finChargeDao.dealPayFinish( finCharge );
		
		if ( finCharge.getType() == FinChargeType.consumeOrder.getId() ) {	// 会员消费
			
			consumeOrderService.dealPayFinish( finCharge.getConsumeOrder() );
			
		}
		
	}
	
	
}
