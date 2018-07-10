package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerEmplDao;
import com.djy.co.model.CoPartnerEmpl;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerStaffDao")
public class CoPartnerEmplDaoImpl extends BaseDaoImpl<CoPartnerEmpl> implements CoPartnerEmplDao{

	@Override
	public List<CoPartnerEmpl> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartnerEmpl.class.getName() + " copEmpl WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String emplID = (String)param.get("emplID");
		if ( !StringUtil.isEmpty( emplID ) ) {
			hql += " AND copEmpl.emplID LIKE :emplID";
			params.put("emplID", "%%" + emplID + "%%");
		}
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND copEmpl.realName LIKE :realName";
			params.put("realName", "%%" + realName + "%%");
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND copEmpl.user.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND copEmpl.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND copEmpl.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND copEmpl.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( copEmpl.emplID LIKE :searchText"
				+ " OR copEmpl.realName LIKE :searchText" 
				+ " OR copEmpl.user.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY copEmpl." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY copEmpl.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}
	
	@Override
	public int updateCoPartnerEmplStatus(Integer[] ids, int status) {
		if(ids.length > 0){
			String hql = "UPDATE " + CoPartnerEmpl.class.getName() + " copEmpl SET copEmpl.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

}
