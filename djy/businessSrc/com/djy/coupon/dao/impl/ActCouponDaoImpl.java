package com.djy.coupon.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.coupon.dao.ActCouponDao;
import com.djy.coupon.enumtype.ActCouponStatus;
import com.djy.coupon.model.ActCoupon;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("actCouponDao")
public class ActCouponDaoImpl extends BaseDaoImpl<ActCoupon> implements ActCouponDao{

	@Override
	public List<ActCoupon> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + ActCoupon.class.getName() + " actCoupon WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND actCoupon.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		String name = (String)param.get("name");
		if(!StringUtil.isEmpty( name )){
				
			hql += " AND actCoupon.coPartner.name LIKE :name";
			params.put("name", "%%" + name + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND actCoupon.startTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND actCoupon.endTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND actCoupon.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (actCoupon.title LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY actCoupon." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY actCoupon.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public int updateActCouponStatus(Integer[] ids, Integer status) {
		if(ids.length > 0){
			String hql = "UPDATE " + ActCoupon.class.getName() + " actCoupon SET actCoupon.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

	@Override
	public List<ActCoupon> getActsByPage(int page) {
        String hql = "";
		hql =" FROM " + ActCoupon.class.getName() + " con  WHERE 1=1";
		hql += " AND con.status = "+ActCouponStatus.normal.getId();
		hql += " AND con.coPartner.status = "+CoPartnerStatus.normal.getId();
		hql += " ORDER BY con.insertTime ";
		hql += " DESC";
		return this.find(hql, page, 5);
	}
	
}
