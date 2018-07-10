package com.djy.co.service;

import com.djy.co.model.CoPartnerAccount;
import com.djy.co.model.CoSettleOrder;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.service.BaseService;

public interface CoPartnerAccountService extends BaseService<CoPartnerAccount>{

	Double updateBySysDepositOrder(CoSysDepositOrder coSysDepositOrder);

	Double updateByConsumeOrder(ConsumeOrder consumeOrder);
	
	void updateByCoSettleOrder(CoSettleOrder coSettleOrder);
	
}
