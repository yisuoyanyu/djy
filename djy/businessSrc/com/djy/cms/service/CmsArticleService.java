package com.djy.cms.service;

import java.util.List;
import java.util.Map;

import com.djy.cms.model.CmsArticle;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CmsArticleService extends BaseService<CmsArticle>{

	List<CmsArticle> search(PagingBean pb, Map<String, Object> param);

	int delCmsArticle(Integer[] ids);

	int disableCmsArticle(Integer[] ids);

	int enableCmsArticle(Integer[] ids);

}
