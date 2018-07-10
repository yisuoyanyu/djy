package com.djy.spread.service;

import java.util.List;
import java.util.Map;

import com.djy.consume.model.ConsumeOrder;
import com.djy.spread.model.SpreadCommission;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface SpreadCommissionService extends BaseService<SpreadCommission>{

	SpreadCommission getCommByUser(User user);

	List<SpreadCommission> search(PagingBean pb, Map<String, Object> param);
	
	/**
	 * 统计推广佣金
	 * @param param
	 * @return
	 */
	double countCommissionTotalAmount(Map<String, Object> param);
	
	List<SpreadCommission> getCommByUser(User user,Integer page);
	
	long getTotalNumByUser(User user);
	
	void dealFirstPaySuccess(ConsumeOrder consumeOrder);
}
