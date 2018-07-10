package com.djy.cms.dao;

import java.util.List;
import java.util.Map;

import com.djy.cms.model.CmsArticle;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CmsArticleDao extends BaseDao<CmsArticle>{

	List<CmsArticle> search(PagingBean pb, Map<String, Object> param);

	int updateCmsArticleStatus(Integer[] ids, int status);

}
