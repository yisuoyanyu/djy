package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoSysDepositLogService extends BaseService<CoSysDepositLog>{

	List<CoSysDepositLog> search(PagingBean pb, Map<String, Object> param);

	Double search(Map<String, Object> param);

	List<CoSysDepositLog> searchByCoPartnerId( Integer coPartnerId ,Integer page);
	
	void logConsumeOrder( ConsumeOrder consumeOrder );
	
	void logCoSysDeposit( CoSysDepositOrder coSysDepositOrder );
	
	/**
	 * 获取交接时间段内的期末平台预存额
	 * @param param
	 * @return
	 */
	double getCoSysDepositLogEndSysDeposit(Map<String, Object> param);

	List<CoSysDepositLog> searchAllCoSysDepositLogs(Map<String, Object> param);
	
	/**
	 * 获取交接时间段内的期初平台预存额
	 * @param param
	 * @return
	 */
	double getCoSysDepositLogStartSysDeposit(Map<String, Object> param);
	
	
	
}
