package com.djy.co.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerAdTagDao;
import com.djy.co.model.CoPartnerAdTag;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerAdTagDao")
public class CoPartnerAdTagDaoImpl extends BaseDaoImpl<CoPartnerAdTag> implements CoPartnerAdTagDao{

	@Override
	public List<CoPartnerAdTag> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CoPartnerAdTag.class.getName() + " coPartnerAdTag WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND coPartnerAdTag.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		Integer coPartnerAdId = (Integer)param.get("coPartnerAdId");
		if ( !StringUtil.isEmpty( coPartnerAdId ) ) {
			hql += " AND coPartnerAdTag.coPartnerAd.id = :coPartnerAdId";
			params.put("coPartnerAdId", coPartnerAdId);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (coPartnerAdTag.title LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY coPartnerAdTag." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY coPartnerAdTag.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
