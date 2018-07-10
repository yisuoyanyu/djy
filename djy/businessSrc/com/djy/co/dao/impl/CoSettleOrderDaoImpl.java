package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSettleOrderDao;
import com.djy.co.enumtype.CoSettleOrderStatus;
import com.djy.co.model.CoSettleOrder;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coSettleOrderDao")
public class CoSettleOrderDaoImpl extends BaseDaoImpl<CoSettleOrder> implements CoSettleOrderDao{
	
	@Override
	public List<CoSettleOrder> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoSettleOrder.class.getName() + " coSetOrder WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND coSetOrder.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND coSetOrder.status = :status";
			params.put("status", status);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSetOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSetOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (coSetOrder.coPartner.name LIKE :searchText "
					+ " OR coSetOrder.no LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coSetOrder." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coSetOrder.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public double getCoSettleOrderAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(coSetOrder.amount) FROM " + CoSettleOrder.class.getName() + " coSetOrder WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql += " AND coSetOrder.status = :status";
		params.put("status", CoSettleOrderStatus.paySuccess.getId());
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSetOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSetOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSetOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		double totalAmount = 0;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	@Override
	public double getInvitedCoSettleAmountByCoParnerId(Map<String, Object> param) {
		String sql = "SELECT SUM(coSetOrder.amount) FROM co_settle_order coSetOrder WHERE EXISTS ("
				+ " 	SELECT con.id conOrderId FROM consume_order con WHERE EXISTS ( "
				+ " 		SELECT u.ID FROM USER u WHERE u.ofCoPartner =:coPartnerId AND u.id = con.user_ID"
				+ " 	)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			sql += " AND con.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			sql += " AND con.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			params.put("coPartnerId", coPartnerId);
			
			sql += " AND con.co_partner_ID =:partnerId";
			params.put("partnerId", coPartnerId);
		}
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		sql += " AND coSetOrder.consume_order_ID = con.id )";
		
		if (this.getUniqueBySql(sql, params) != null) {
			return Double.valueOf(((Number)this.getUniqueBySql(sql, params)).doubleValue());
		} else {
			return 0d;
		}
	}

}
