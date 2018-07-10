package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSysDepositLogDao;
import com.djy.co.model.CoSysDepositLog;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coSysDepositLogDao")
public class CoSysDepositLogDaoImpl extends BaseDaoImpl<CoSysDepositLog> implements CoSysDepositLogDao{

	@Override
	public List<CoSysDepositLog> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoSysDepositLog.class.getName() + " coSysDepLog WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND coSysDepLog.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Integer incomeExpense = (Integer)param.get("incomeExpense");
		if ( !StringUtil.isEmpty( incomeExpense ) ) {
			hql += " AND coSysDepLog.incomeExpense = :incomeExpense";
			params.put("incomeExpense", incomeExpense );
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepLog.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepLog.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepLog.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Integer coSysDepositOrderId = (Integer)param.get("coSysDepositOrderId");
		if ( !StringUtil.isEmpty( coSysDepositOrderId ) ) {
			hql += " AND coSysDepLog.coSysDepositOrder.id = :coSysDepositOrderId";
			params.put("coSysDepositOrderId", coSysDepositOrderId);
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coSysDepLog." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coSysDepLog.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public Double search(Map<String, Object> param) {
		String hql = "SELECT SUM(coSysDepLog.amount) FROM " + CoSysDepositLog.class.getName() + " coSysDepLog WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND coSysDepLog.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Integer incomeExpense = (Integer)param.get("incomeExpense");
		if ( !StringUtil.isEmpty( incomeExpense ) ) {
			hql += " AND coSysDepLog.incomeExpense = :incomeExpense";
			params.put("incomeExpense", incomeExpense );
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepLog.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepLog.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepLog.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	@Override
	public List<CoSysDepositLog> searchByCoPartnerId(Integer coPartnerId, Integer page) {
		String hql =" FROM " + CoSysDepositLog.class.getName() + " con "+"WHERE 1=1";
		hql += " AND con.coPartner.id = "+coPartnerId;
        hql += " ORDER BY insertTime ";
		hql += " DESC";

		return this.find(hql, page, 10);
	}
	
	@Override
	public double getCoSysDepositLogStartSysDeposit(Map<String, Object> param) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT csdLog.sysDeposit FROM co_sys_deposit_log csdLog WHERE EXISTS "
				+ " ( "
				+ " SELECT * FROM "
				+ " ( "
				+ " SELECT MIN(coLog.insertTime) minTime,coLog.co_partner_ID copId FROM co_sys_deposit_log coLog WHERE 1=1 ";
			
				Integer coPartnerId = (Integer)param.get("coPartnerId");
				if ( !StringUtil.isEmpty( coPartnerId ) ) {
					sql += " AND coLog.co_partner_ID =:coPartnerId ";
					params.put("coPartnerId", coPartnerId);
				}
				
				Date startTime = (Date)param.get("timeStart");
				if ( !StringUtil.isEmpty( startTime ) ) {
					sql += " AND coLog.insertTime >= :startTime ";
					params.put("startTime", startTime);
				}
				
				Date endTime = (Date)param.get("timeEnd");
				if ( !StringUtil.isEmpty( endTime ) ) {
					sql += " AND coLog.insertTime <= :endTime ";
					params.put("endTime", endTime);
				}
				
				sql += " ) AS T WHERE csdLog.co_partner_ID = T.copId AND csdLog.insertTime < T.minTime "
				+ " ) ORDER BY csdLog.insertTime DESC LIMIT 1";
		
		
		double sysDeposit = 0d;
		if (this.getUniqueBySql(sql,params) != null) {
			 sysDeposit = (double) this.getUniqueBySql(sql,params);
		}
		
		return sysDeposit;
	}

	@Override
	public double getCoSysDepositLogEndSysDeposit(Map<String, Object> param) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT csdLog.sysDeposit FROM co_sys_deposit_log csdLog WHERE EXISTS "
				+ " ( SELECT * FROM "
				+ " ( "
				+ " SELECT MAX(coLog.insertTime) maxTime,coLog.co_partner_ID copId FROM co_sys_deposit_log coLog WHERE 1=1 ";
				
				Integer coPartnerId = (Integer)param.get("coPartnerId");
				if ( !StringUtil.isEmpty( coPartnerId ) ) {
					sql += " AND coLog.co_partner_ID =:coPartnerId ";
					params.put("coPartnerId", coPartnerId);
				}
		
				Date startTime = (Date)param.get("timeStart");
				if ( !StringUtil.isEmpty( startTime ) ) {
					sql += " AND coLog.insertTime >= :startTime ";
					params.put("startTime", startTime);
				}
				Date endTime = (Date)param.get("timeEnd");
				if ( !StringUtil.isEmpty( endTime ) ) {
					sql += " AND coLog.insertTime <= :endTime ";
					params.put("endTime", endTime);
				}
				
				sql += " ) AS T WHERE csdLog.co_partner_ID = T.copId AND csdLog.insertTime = T.maxTime "
				+ " )";
		
		
		
		double sysDeposit = 0d;
		if (this.getUniqueBySql(sql,params) != null) {
			 sysDeposit = (double) this.getUniqueBySql(sql,params);
		}
		
		return sysDeposit;
	}

	@Override
	public List<CoSysDepositLog> searchAllCoSysDepositLogs(Map<String, Object> param) {
		String hql = " FROM " + CoSysDepositLog.class.getName() + " coSysDepLog WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coSysDepLog.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coSysDepLog.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coSysDepLog.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coSysDepLog." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coSysDepLog.id DESC";
		}
		
		return this.find(hql, params);
	}


	
}
