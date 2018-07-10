package com.djy.co.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerTagDao;
import com.djy.co.model.CoPartnerTag;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerTagDao")
public class CoPartnerTagDaoImpl extends BaseDaoImpl<CoPartnerTag> implements CoPartnerTagDao{

	@Override
	public List<CoPartnerTag> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartnerTag.class.getName() + " coPartnerTag WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND coPartnerTag.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND coPartnerTag.coPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (coPartnerTag.title LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coPartnerTag." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coPartnerTag.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
