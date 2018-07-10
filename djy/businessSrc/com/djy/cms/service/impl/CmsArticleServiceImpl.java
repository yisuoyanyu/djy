package com.djy.cms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.cms.dao.CmsArticleDao;
import com.djy.cms.enumtype.CmsArticleStatus;
import com.djy.cms.model.CmsArticle;
import com.djy.cms.model.CmsCatg;
import com.djy.cms.service.CmsArticleService;
import com.djy.cms.service.CmsCatgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("cmsArticleService")
public class CmsArticleServiceImpl extends BaseServiceImpl<CmsArticle> implements CmsArticleService{
	
	@Autowired
	private CmsCatgService cmsCatgService;
	
	@Autowired
	private CmsArticleDao cmsArticleDao;
	
	@Override
	public List<CmsArticle> search(PagingBean pb, Map<String, Object> param) {
		String cmsCatgId = (String)param.get("cmsCatgId");
		if ( !StringUtil.isEmpty( cmsCatgId ) ) {
			CmsCatg cmsCatg = cmsCatgService.get(Integer.parseInt(cmsCatgId));
			
			List<CmsCatg> cmsCatgs = cmsCatgService.findByCmsCatg(cmsCatg);
			Integer [] cmsCatgIds = new Integer[cmsCatgs.size()];
			if (cmsCatgs.size() > 0) {
				for (int i = 0; i < cmsCatgs.size(); i++) {
					cmsCatgIds[i] = cmsCatgs.get(i).getId();
				}
			}
			
			param.put("cmsCatgIds", cmsCatgIds);
		}
		return this.cmsArticleDao.search(pb, param);
	}

	@Override
	public int delCmsArticle(Integer[] ids) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("id", ids, SqlOperator.in);
		int num = this.deleteByFilter(filter);
		return num;
	}

	@Override
	public int disableCmsArticle(Integer[] ids) {
		return cmsArticleDao.updateCmsArticleStatus(ids, CmsArticleStatus.freeze.getId());
	}

	@Override
	public int enableCmsArticle(Integer[] ids) {
		return cmsArticleDao.updateCmsArticleStatus(ids, CmsArticleStatus.normal.getId());
	}

}
