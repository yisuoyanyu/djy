package com.djy.fin.service;

import com.djy.co.model.CoSettleOrder;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.fin.model.FinTransfer;
import com.djy.spread.model.SpreadCommission;
import com.frame.base.service.BaseService;

public interface FinTransferService extends BaseService<FinTransfer> {
	
	FinTransfer createByCoSettle(CoSettleOrder coSettleOrder);
	
	FinTransfer createByCoSysDeposit(CoSysDepositOrder coSysDepositOrder);
	
	/**
	 * 根据推广佣金进行企业转账
	 * @param spreadCommission
	 * @return
	 */
	FinTransfer createBySpreadCommison(SpreadCommission spreadCommission);
}
