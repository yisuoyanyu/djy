package com.djy.co.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerAdDao;
import com.djy.co.model.CoPartnerAd;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerAdDao")
public class CoPartnerAdDaoImpl extends BaseDaoImpl<CoPartnerAd> implements CoPartnerAdDao{

	@Override
	public int updateCoPartnerAdStatus(Integer[] ids, Integer status) {
		if(ids.length > 0){
			String hql = "UPDATE " + CoPartnerAd.class.getName() + " copAd SET copAd.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

	@Override
	public List<CoPartnerAd> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartnerAd.class.getName() + " copAd WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND copAd.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND copAd.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND copAd.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND copAd.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (copAd.title LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY copAd." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY copAd.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
