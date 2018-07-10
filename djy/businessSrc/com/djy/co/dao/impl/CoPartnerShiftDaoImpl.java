package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerShiftDao;
import com.djy.co.model.CoPartnerShift;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerShiftDao")
public class CoPartnerShiftDaoImpl extends BaseDaoImpl<CoPartnerShift> implements CoPartnerShiftDao{

	@Override
	public List<CoPartnerShift> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartnerShift.class.getName() + " coShift WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coShift.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND coShift.startTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND coShift.endTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coShift." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coShift.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public String getLastCoPartnerShiftHandoverTime(Map<String, Object> param) {
		
		String hql = "SELECT MAX(endTime) FROM " + CoPartnerShift.class.getName() + " coShift WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coShift.coPartner.id =:coPartnerId ";
			params.put("coPartnerId", coPartnerId);
		}
		
		String handoverTimeStart = null;
		//存在最近的交接时间
		if (this.getUniqueByHql(hql,params) != null) {
			String strHandoverTimeStart = this.getUniqueByHql(hql,params).toString();
			handoverTimeStart = strHandoverTimeStart.substring(0, strHandoverTimeStart.length() -2);
		}
		
		return handoverTimeStart;
	}

}
