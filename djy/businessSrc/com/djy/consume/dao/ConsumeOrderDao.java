package com.djy.consume.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerEmpl;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface ConsumeOrderDao extends BaseDao<ConsumeOrder>{

	List<ConsumeOrder> search(PagingBean pb, Map<String, Object> param);

	List<ConsumeOrder> searchByCoPartnerId(Integer coPartnerId,Integer page);
	
	List<ConsumeOrder> getEmplOrders(CoPartnerEmpl coPartnerEmpl, int page);
	
	void dealPayFinish(ConsumeOrder consumeOrder);

	double getConsumeOrderAmount(Map<String, Object> param);

	double getConsumeOrderPayAmount(Map<String, Object> param);

	List<ConsumeOrder> search(Map<String, Object> param);

	List<ConsumeOrder> searchFirstConsumeUser(PagingBean pb, Map<String, Object> param);

	Long getConsumeOrderNum(Map<String, Object> param);

	double getInvitedConsumeOrderAmount(Map<String, Object> param);

	double getInvitedConsumeOrderPayAmount(Map<String, Object> param);

	Long getConsumeOrderNumByCoParnerId(Map<String, Object> param);

	double getInvitedConsumeOrderAmountByCoParnerId(Map<String, Object> param);

	double getSponsorConsumeOrderPayAmountByCoParnerId(Map<String, Object> param);

	Long getAllConsumeOrderNum(Map<String, Object> param);
	
}
