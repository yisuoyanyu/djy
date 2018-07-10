package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSettleOrder;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoSettleOrderDao extends BaseDao<CoSettleOrder>{

	List<CoSettleOrder> search(PagingBean pb, Map<String, Object> param);

	double getCoSettleOrderAmount(Map<String, Object> param);

	double getInvitedCoSettleAmountByCoParnerId(Map<String, Object> param);

}
