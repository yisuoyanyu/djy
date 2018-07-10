package com.djy.consume.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartnerEmpl;
import com.djy.consume.dao.ConsumeOrderDao;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("consumeOrderDao")
public class ConsumeOrderDaoImpl extends BaseDaoImpl<ConsumeOrder> implements ConsumeOrderDao{
	
	@Override
	public List<ConsumeOrder> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + ConsumeOrder.class.getName() + " consumeOrder WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String mobile = (String)param.get("userMobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND consumeOrder.user.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		String nickname = (String)param.get("nickname");
		if ( !StringUtil.isEmpty( nickname ) ) {
			hql += " AND consumeOrder.user.wechatUser.nickname LIKE :nickname";
			params.put("nickname", "%%" + nickname + "%%");
		}
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND consumeOrder.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		String emplID = (String)param.get("emplID");
		if ( !StringUtil.isEmpty( emplID ) ) {
			hql += " AND consumeOrder.coPartnerEmpl.emplID =:emplID";
			params.put("emplID", emplID);
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND consumeOrder.status = :status";
			params.put("status", status);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND consumeOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Integer coPartnerEmplId = (Integer)param.get("coPartnerEmplId");
		if ( !StringUtil.isEmpty( coPartnerEmplId ) ) {
			hql += " AND consumeOrder.coPartnerEmpl.id = :coPartnerEmplId";
			params.put("coPartnerEmplId", coPartnerEmplId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND consumeOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND consumeOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (consumeOrder.user.mobile LIKE :searchText"
					+ " OR consumeOrder.user.wechatUser.nickname LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY consumeOrder." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY consumeOrder.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public List<ConsumeOrder> searchByCoPartnerId(Integer coPartnerId, Integer page) {
		String hql =" FROM " + ConsumeOrder.class.getName() + " con "+"WHERE 1=1";
		hql += " AND con.coPartner.id = "+coPartnerId;
		hql += " AND con.status = "+ConsumeOrderStatus.paySuccess.getId();
        hql += " ORDER BY insertTime ";
		hql += " DESC";

		return this.find(hql, page, 10);
	}
	
	
	
	
	@Override
	public List<ConsumeOrder> getEmplOrders(CoPartnerEmpl coPartnerEmpl, int page) {
		String hql =" FROM " + ConsumeOrder.class.getName() + " con "+"WHERE 1=1";
		hql += " AND con.coPartnerEmpl.id = "+coPartnerEmpl.getId();
        hql += " ORDER BY insertTime ";
		hql += " DESC";

		return this.find(hql, page, 10);
	}

	@Override
	public void dealPayFinish(ConsumeOrder consumeOrder) {
		
		String hql = "UPDATE " + ConsumeOrder.class.getName() + " order SET "
				+ " order.status=" + ConsumeOrderStatus.paying.getId()
				+ " WHERE order.id=" + consumeOrder.getId() 
				+ " and order.status=" + ConsumeOrderStatus.unpaid.getId();
		
		this.executeHql(hql);
		
	}

	@Override
	public double getConsumeOrderAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(consumeOrder.consumeAmount) FROM " + ConsumeOrder.class.getName() + " consumeOrder WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql += " AND consumeOrder.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND consumeOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND consumeOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND consumeOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	@Override
	public double getConsumeOrderPayAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(consumeOrder.payAmount) FROM " + ConsumeOrder.class.getName() + " consumeOrder WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		hql += " AND consumeOrder.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND consumeOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND consumeOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND consumeOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	@Override
	public List<ConsumeOrder> search(Map<String, Object> param) {
		String hql = " FROM " + ConsumeOrder.class.getName() + " consumeOrder WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String mobile = (String)param.get("userMobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND consumeOrder.user.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		String nickname = (String)param.get("nickname");
		if ( !StringUtil.isEmpty( nickname ) ) {
			hql += " AND consumeOrder.user.wechatUser.nickname LIKE :nickname";
			params.put("nickname", "%%" + nickname + "%%");
		}
		
		String coPartnerName = (String)param.get("coPartnerName");
		if ( !StringUtil.isEmpty( coPartnerName ) ) {
			hql += " AND consumeOrder.coPartner.name LIKE :coPartnerName";
			params.put("coPartnerName", "%%" + coPartnerName + "%%");
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND consumeOrder.status = :status";
			params.put("status", status);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND consumeOrder.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND consumeOrder.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND consumeOrder.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (consumeOrder.user.mobile LIKE :searchText"
					+ " OR consumeOrder.user.wechatUser.nickname LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY consumeOrder." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY consumeOrder.id DESC";
		}
		
		return this.find(hql, params);
	}


	@Override
	public List<ConsumeOrder> searchFirstConsumeUser(PagingBean pb, Map<String, Object> param) {
		
		/*String sql = "SELECT * FROM consume_order consumeOrder WHERE "
				+ " EXISTS("
				+ " SELECT * FROM ( "
				+ " SELECT consumeOrder.user_ID AS user_ID, MIN(consumeOrder.InsertTime) AS InsertTime "
				+ " FROM consume_order consumeOrder "
				+ " WHERE consumeOrder.status=3  "
				+ " AND EXISTS( "
				+ " SELECT user.id FROM USER WHERE user.ID=consumeOrder.user_ID "
				+ " AND user.sponsor=(SELECT user.id FROM USER WHERE user.mobile='15359029061') "
				+ " ) "
				+ " GROUP BY consumeOrder.user_ID HAVING MIN(consumeOrder.InsertTime) >= '2017-09-03 21:02:45' "
				+ " ) AS res WHERE consumeOrder.user_ID=res.user_ID AND consumeOrder.InsertTime=res.InsertTime "
				+ ")";
		*/
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = " SELECT consumeOrder.* FROM  ( "
				+ " SELECT consumeOrder.user_ID AS user_ID, MIN(consumeOrder.insertTime) AS insertTime "
				+ " FROM consume_order consumeOrder "
				+ " WHERE consumeOrder.status=3 "
				+ " AND EXISTS( "
				+ " SELECT user.id FROM USER WHERE user.sponsor IS NOT NULL AND user.ID=consumeOrder.user_ID ";
				
				String sponsorMobile = (String)param.get("sponsorMobile");
				if ( !StringUtil.isEmpty( sponsorMobile ) ) {
					sql += " AND user.sponsor=(SELECT user.id FROM USER WHERE user.mobile =:sponsorMobile) ";
					params.put("sponsorMobile", sponsorMobile);
				}
				Integer sponsorUserId = (Integer)param.get("sponsorUserId");
				if ( !StringUtil.isEmpty( sponsorUserId ) ) {
					sql += "AND user.sponsor =:sponsorUserId";
					params.put("sponsorUserId", sponsorUserId);
				}
				
				sql += " ) GROUP BY consumeOrder.user_ID ";
				
				Date startTime = (Date)param.get("timeStart");
				Date endTime = (Date)param.get("timeEnd");
				
				if ( !StringUtil.isEmpty( startTime ) || !StringUtil.isEmpty( endTime ) ) {
					sql += " HAVING ";
				}
				if ( !StringUtil.isEmpty( startTime )) {
					sql +=  " MIN(consumeOrder.InsertTime) >= :startTime ";
					params.put("startTime", startTime);
				}
				if ( !StringUtil.isEmpty( endTime ) ) {
					sql += " AND MIN(consumeOrder.InsertTime) <= :endTime ";
					params.put("endTime", endTime);
				}
				sql += " ) AS res "
				+ " LEFT JOIN consume_order consumeOrder "
				+ " ON consumeOrder.user_ID = res.user_ID AND consumeOrder.insertTime=res.insertTime";
		
		
		/*String sql = "SELECT * FROM consume_order conOrder WHERE EXISTS "
					+ "( SELECT T.userId, T.minTime FROM ( "
					+ " SELECT con.user_ID userId, MIN(con.insertTime) minTime FROM consume_order con WHERE EXISTS "
					+ " ( SELECT u.id FROM USER u WHERE u.sponsor IS NOT NULL AND u.id = con.user_ID ) "
					+ " AND con.status = 3 GROUP BY con.user_ID"
					+ " ) AS T "
					+ " WHERE T.minTime = conOrder.insertTime AND T.userId = conOrder.user_ID "
					+ ")";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sponsorMobile = (String)param.get("sponsorMobile");
		if ( !StringUtil.isEmpty( sponsorMobile ) ) {
			sql += " AND EXISTS ("
					+ " SELECT * FROM ( "
					+ " SELECT u2.id u2Id FROM USER u2 WHERE u2.sponsor = ( "
					+ " SELECT u1.id FROM USER u1 WHERE u1.mobile =:sponsorMobile "
					+ " ) "
					+ " )AS T1 "
					+ " WHERE T1.u2Id = conOrder.user_ID "
					+ " ) ";
			params.put("sponsorMobile", sponsorMobile);
		}
		
		Integer sponsorUserId = (Integer)param.get("sponsorUserId");
		if ( !StringUtil.isEmpty( sponsorUserId ) ) {
			sql += " AND EXISTS ("
					+ " SELECT * FROM ( "
					+ " SELECT u2.id u2Id FROM USER u2 WHERE u2.sponsor = ( "
					+ " SELECT u1.id FROM USER u1 WHERE u1.id =:sponsorUserId "
					+ " ) "
					+ " )AS T1 "
					+ " WHERE T1.u2Id = conOrder.user_ID "
					+ " ) ";
			params.put("sponsorUserId", sponsorUserId);
		}
		
		String insertTimeStart = (String)param.get("insertTimeStart");
		if ( !StringUtil.isEmpty( insertTimeStart ) ) {
			Date date = DateUtil.toDate(insertTimeStart);
			Date insTimeStart = DateUtil.getFirstTimeOfDate(date);
			sql += " AND conOrder.insertTime >= :insTimeStart";
			params.put("insTimeStart", insTimeStart);
		}
		
		String insertTimeEnd = (String)param.get("insertTimeEnd");
		if ( !StringUtil.isEmpty( insertTimeEnd ) ) {
			Date date = DateUtil.toDate(insertTimeEnd);
			Date insTimeEnd = DateUtil.getLastTimeOfDate(date);
			sql += " AND conOrder.insertTime <= :insTimeEnd";
			params.put("insTimeEnd", insTimeEnd);
		}*/
		
		return this.findBySql(sql, params, pb);
	}

	@Override
	public Long getConsumeOrderNum(Map<String, Object> param) {
		String sql = "SELECT count(con.id) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.sponsor IS NOT NULL AND u.id = con.user_ID)";
		
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
			sql += " AND con.co_partner_ID = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		return Long.valueOf(((Number)this.getUniqueBySql(sql, params)).longValue());
	}

	@Override
	public double getInvitedConsumeOrderAmount(Map<String, Object> param) {
		String sql = "SELECT SUM(con.consumeAmount) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.sponsor IS NOT NULL AND u.id = con.user_ID)";
		
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
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		if (this.getUniqueBySql(sql, params) != null) {
			return Double.valueOf(((Number)this.getUniqueBySql(sql, params)).doubleValue());
		} else {
			return 0d;
		}
	}

	@Override
	public double getInvitedConsumeOrderPayAmount(Map<String, Object> param) {
		String sql = "SELECT SUM(con.payAmount) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.sponsor IS NOT NULL AND u.id = con.user_ID)";
		
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
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		if (this.getUniqueBySql(sql, params) != null) {
			return Double.valueOf(((Number)this.getUniqueBySql(sql, params)).doubleValue());
		} else {
			return 0d;
		}
	}

	@Override
	public Long getConsumeOrderNumByCoParnerId(Map<String, Object> param) {
		String sql = "SELECT count(con.id) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.ofCoPartner =:coPartnerId AND u.id = con.user_ID)";
		
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
		
		return Long.valueOf(((Number)this.getUniqueBySql(sql, params)).longValue());
	}

	@Override
	public double getInvitedConsumeOrderAmountByCoParnerId(Map<String, Object> param) {
		String sql = "SELECT SUM(con.consumeAmount) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.ofCoPartner =:coPartnerId AND u.id = con.user_ID)";
		
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
		
		if (this.getUniqueBySql(sql, params) != null) {
			return Double.valueOf(((Number)this.getUniqueBySql(sql, params)).doubleValue());
		} else {
			return 0d;
		}
	}

	@Override
	public double getSponsorConsumeOrderPayAmountByCoParnerId(Map<String, Object> param) {
		String sql = "SELECT SUM(con.payAmount) FROM consume_order con WHERE EXISTS ("
				+ " SELECT u.ID FROM USER u WHERE u.ofCoPartner =:coPartnerId AND u.id = con.user_ID)";
		
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
		}
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		if (this.getUniqueBySql(sql, params) != null) {
			return Double.valueOf(((Number)this.getUniqueBySql(sql, params)).doubleValue());
		} else {
			return 0d;
		}
	}

	@Override
	public Long getAllConsumeOrderNum(Map<String, Object> param) {
		String sql = "SELECT count(con.id) FROM consume_order con WHERE 1=1 ";
		
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
			sql += " AND con.co_partner_ID = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		sql +=" AND con.status = :status";
		params.put("status", ConsumeOrderStatus.paySuccess.getId());
		
		return Long.valueOf(((Number)this.getUniqueBySql(sql, params)).longValue());
	}

}
