package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoSysDepositLog;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoSysDepositLogDao extends BaseDao<CoSysDepositLog>{

	List<CoSysDepositLog> search(PagingBean pb, Map<String, Object> param);

	Double search(Map<String, Object> param);

	List<CoSysDepositLog> searchByCoPartnerId( Integer coPartnerId ,Integer page);
	
	double getCoSysDepositLogStartSysDeposit(Map<String, Object> param);
	
	double getCoSysDepositLogEndSysDeposit(Map<String, Object> param);

	List<CoSysDepositLog> searchAllCoSysDepositLogs(Map<String, Object> param);

}
