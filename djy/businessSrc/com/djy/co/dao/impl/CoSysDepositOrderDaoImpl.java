package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSysDepositOrderDao;
import com.djy.co.enumtype.CoSysDepositOrderStatus;
import com.djy.co.model.CoSysDepositOrder;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coSysDepositOrderDao")
public class CoSysDepositOrderDaoImpl extends BaseDaoImpl<CoSysDepositOrder> implements CoSysDepositOrderDao{
	
	@Override
	public List<CoSysDepositOrder> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoSysDepositOrder.class.getName() + " coSysDepOrder WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND coSysDepOrder.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND coSysDepOrder.status = :status";
			params.put("status", status);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (coSysDepOrder.coPartner.name LIKE :searchText "
					+ " OR coSysDepOrder.no LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coSysDepOrder." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coSysDepOrder.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public double getCoSysDepositOrderAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(coSysDepositOrder.amount) FROM " + CoSysDepositOrder.class.getName() + " coSysDepositOrder WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql += " AND coSysDepositOrder.status = :status";
		params.put("status", CoSysDepositOrderStatus.paySuccess.getId());
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepositOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepositOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepositOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	@Override
	public List<CoSysDepositOrder> search(Map<String, Object> param) {
		String hql = " FROM " + CoSysDepositOrder.class.getName() + " coSysDepOrder WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND coSysDepOrder.status = :status";
			params.put("status", status);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coSysDepOrder." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coSysDepOrder.id DESC";
		}
		
		return this.find(hql, params);
	}

	@Override
	public double getCoSysDepositPayOrderAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(coSysDepositOrder.payAmount) FROM " + CoSysDepositOrder.class.getName() + " coSysDepositOrder WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql += " AND coSysDepositOrder.status = :status";
		params.put("status", CoSysDepositOrderStatus.paySuccess.getId());
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepositOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepositOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepositOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

}
