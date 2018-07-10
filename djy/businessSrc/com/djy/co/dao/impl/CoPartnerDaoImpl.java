package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerDao;
import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerDao")
public class CoPartnerDaoImpl extends BaseDaoImpl<CoPartner> implements CoPartnerDao{
	
	
	@Override
	public List<CoPartner> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartner.class.getName() + " cop WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer status = (Integer)param.get("status");
		if ( !StringUtil.isEmpty( status ) ) {
			hql += " AND cop.status = :status";
			params.put("status", status );
		}
		
		String name = (String)param.get("name");
		if ( !StringUtil.isEmpty( name ) ) {
			hql += " AND cop.name LIKE :name";
			params.put("name", "%%" + name + "%%");
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND cop.user.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND cop.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND cop.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (cop.name LIKE :searchText"
					+ " OR cop.user.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY cop." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY cop.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public List<CoPartner> getConsByDistance(Integer page,String lats, String lons) {
		
		String hql =" FROM " + CoPartner.class.getName() + " con "+"WHERE 1=1";
		hql += " AND con.status = "+CoPartnerStatus.normal.getId();
        hql += " ORDER BY ";
		hql += "ROUND(6378.138*2*ASIN(SQRT(POW(SIN(("+lats+ "*PI()/180-lat*PI()/180)/2),2)+COS("+lats+"*PI()/180)*COS(lat*PI()/180)*POW(SIN(("+lons+"*PI()/180-lon*PI()/180)/2),2)))*1000)";
		hql += " ASC";

		return this.find(hql, page, 5);
	}
	
	@Override
	public List<CoPartner> getConsByPage(Integer page) {
		String hql =" FROM " + CoPartner.class.getName() + " con "+"WHERE 1=1";
		hql += " AND con.status = "+CoPartnerStatus.normal.getId();

		return this.find(hql, page, 5);
	}

	@Override
	public List<CoPartner> getAdsByDistance(Integer page, String lat, String lon) {
		String hql = "select distinct(con) FROM " + CoPartner.class.getName() + " con,"+CoPartnerAd.class.getName() + " cad "+"WHERE 1=1";
		hql += " AND con.id = cad.coPartner.id";
		hql += " AND con.status = "+CoPartnerStatus.normal.getId();
		hql += " ORDER BY ";
		hql += "ROUND(6378.138*2*ASIN(SQRT(POW(SIN(("+lat+ "*PI()/180-con.lat*PI()/180)/2),2)+COS("+lat+"*PI()/180)*COS(con.lat*PI()/180)*POW(SIN(("+lon+"*PI()/180-con.lon*PI()/180)/2),2)))*1000)";
		hql += " ASC";

		return this.find(hql, page, 5);
	}

	@Override
	public int updateCoPartnerStatus(Integer[] ids, Integer status) {
		if(ids.length > 0){
			String hql = "UPDATE " + CoPartner.class.getName() + " cop SET cop.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}


}
