package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSysDepositOrder;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoSysDepositOrderDao extends BaseDao<CoSysDepositOrder>{

	List<CoSysDepositOrder> search(PagingBean pb, Map<String, Object> param);

	double getCoSysDepositOrderAmount(Map<String, Object> param);

	List<CoSysDepositOrder> search(Map<String, Object> param);

	double getCoSysDepositPayOrderAmount(Map<String, Object> param);

}
