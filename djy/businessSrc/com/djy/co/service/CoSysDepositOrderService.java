package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoSysDepositOrderService extends BaseService<CoSysDepositOrder>{

	List<CoSysDepositOrder> search(PagingBean pb, Map<String, Object> param);
	
	CoSysDepositOrder createByCoSysDepositLog( CoSysDepositLog coSysDepositLog );
	
	/**
	 * 获取交接时间段内的平台预存总额
	 * @param param
	 * @return
	 */
	double getCoSysDepositOrderAmount(Map<String, Object> param);

	List<CoSysDepositOrder> search(Map<String, Object> param);
	
	/**
	 * 获取交接时间段内的平台预存支付总额
	 * @param param
	 * @return
	 */
	double getCoSysDepositPayOrderAmount(Map<String, Object> param);
	
}
