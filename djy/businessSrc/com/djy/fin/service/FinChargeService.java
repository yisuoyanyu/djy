package com.djy.fin.service;

import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.model.FinCharge;
import com.frame.base.service.BaseService;

public interface FinChargeService extends BaseService<FinCharge> {

	FinCharge getByOrderNo(String orderNo);
	
	//---------------------------------------------
	
	FinCharge createByConsumeOrder(ConsumeOrder consumeOrder, FinChannel finChannel, String payerIp);
	
	//---------------------------------------------
	
	void dealPaySuccess(FinCharge finCharge);
	
	void dealPayFail(FinCharge finCharge);
	
	void dealPayFinish(FinCharge finCharge);
	
}
