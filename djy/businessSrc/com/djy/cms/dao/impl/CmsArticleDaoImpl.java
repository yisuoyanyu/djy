package com.djy.cms.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.cms.dao.CmsArticleDao;
import com.djy.cms.model.CmsArticle;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("cmsArticleDao")
public class CmsArticleDaoImpl extends BaseDaoImpl<CmsArticle> implements CmsArticleDao{

	@Override
	public List<CmsArticle> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CmsArticle.class.getName() + " article WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND article.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		String code = (String)param.get("code");
		if ( !StringUtil.isEmpty( code ) ) {
			hql += " AND article.code LIKE :code";
			params.put("code", "%%" + code + "%%");
		}
		
		Integer [] cmsCatgIds = (Integer[]) param.get("cmsCatgIds");
		if ( !StringUtil.isEmpty( cmsCatgIds ) ) {
			hql += " AND article.cmsCatg.id in (:cmsCatgIds)";
			params.put("cmsCatgIds", cmsCatgIds);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND article.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND article.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( article.title LIKE :searchText )";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY article." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY article.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public int updateCmsArticleStatus(Integer[] ids, int status) {
		String hql = "UPDATE " + CmsArticle.class.getName() + " cmsArticle SET cmsArticle.status=:status WHERE id IN (:ids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("ids", ids);
		
		return this.executeHql(hql, params);
	}

}
