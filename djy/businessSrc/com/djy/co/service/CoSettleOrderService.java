package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSettleOrder;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoSettleOrderService extends BaseService<CoSettleOrder>{

	List<CoSettleOrder> search(PagingBean pb, Map<String, Object> param);
	
	CoSettleOrder createByConsumeOrder(ConsumeOrder consumeOrder);
	
	/**
	 * 获取平台结算总金额
	 * @param param
	 * @return
	 */
	double getCoSettleOrderAmount(Map<String, Object> param);
	
	/**
	 * 获取被邀平台结算总金额
	 * @param param
	 * @return
	 */
	double getInvitedCoSettleAmountByCoParnerId(Map<String, Object> param);
	
}
