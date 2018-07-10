package com.djy.spread.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.spread.dao.SpreadCommissionDao;
import com.djy.spread.model.SpreadCommission;
import com.djy.user.model.User;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("spreadCommissionDao")
public class SpreadCommissionDaoImpl extends BaseDaoImpl<SpreadCommission> implements SpreadCommissionDao{

	@Override
	public List<SpreadCommission> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + SpreadCommission.class.getName() + " spreadCommission WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND spreadCommission.spreadPromoter.realName = :realName";
			params.put("realName", realName);
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND spreadCommission.spreadPromoter.user.mobile = :mobile";
			params.put("mobile", mobile);
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND spreadCommission.status = :status";
			params.put("status", status);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND spreadCommission.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND spreadCommission.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer spreadPromoterId = (Integer)param.get("spreadPromoterId");
		if ( !StringUtil.isEmpty( spreadPromoterId ) ) {
			hql += " AND spreadCommission.spreadPromoter.id = :spreadPromoterId";
			params.put("spreadPromoterId", spreadPromoterId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( spreadCommission.spreadPromoter.realName LIKE :searchText "
					+ " OR spreadCommission.spreadPromoter.user.mobile LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY spreadCommission." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY spreadCommission.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public double countCommissionTotalAmount(Map<String, Object> param) {
		String hql = "SELECT SUM(spreadCommission.amount) FROM " + SpreadCommission.class.getName() + " spreadCommission WHERE 1=1 "; 
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND spreadCommission.spreadPromoter.realName = :realName";
			params.put("realName", realName);
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND spreadCommission.spreadPromoter.user.mobile = :mobile";
			params.put("mobile", mobile);
		}
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND spreadCommission.status = :status";
			params.put("status", status);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND spreadCommission.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND spreadCommission.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer spreadPromoterId = (Integer)param.get("spreadPromoterId");
		if ( !StringUtil.isEmpty( spreadPromoterId ) ) {
			hql += " AND spreadCommission.spreadPromoter.id = :spreadPromoterId";
			params.put("spreadPromoterId", spreadPromoterId);
		}
		
		double totalAmount = 0d;
		if(this.getUniqueByHql(hql, params)!=null){
			totalAmount = (double) this.getUniqueByHql(hql, params);
		}
		
		return totalAmount;
	}

	
	@Override
	public List<SpreadCommission> getCommByUser(User user, Integer page) {
		String hql = "";
	    hql ="select spreadCommission FROM " + SpreadCommission.class.getName() + " spreadCommission "+"WHERE 1=1";
	    hql += " AND spreadCommission.spreadPromoter.id = :userId";
	    hql += " ORDER BY insertTime DESC";
        
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getSpreadPromoter().getId());
		
		return this.find(hql, params, page,10);
	}

	
}
