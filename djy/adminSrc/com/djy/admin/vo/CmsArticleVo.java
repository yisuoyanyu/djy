package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.cms.model.CmsArticle;
import com.frame.base.web.vo.BaseVo;

public class CmsArticleVo extends BaseVo{
	
	private Integer id;
	private String code;
	private String title;
	private String cmsCatgNamePath;
	private String statusName;
	private Date insertTime;
	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getCmsCatgNamePath() {
		return cmsCatgNamePath;
	}


	public void setCmsCatgNamePath(String cmsCatgNamePath) {
		this.cmsCatgNamePath = cmsCatgNamePath;
	}


	public Date getInsertTime() {
		return insertTime;
	}

	
	
	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public static CmsArticleVo transfer(CmsArticle cmsArticle) {
		CmsArticleVo vo = new CmsArticleVo();
		vo.copyProperity( cmsArticle );
		
		if (cmsArticle.getCmsCatg() != null) {
			vo.setCmsCatgNamePath(cmsArticle.getCmsCatg().getNamePath());
		}
		
		return vo;
	}
	
	public static List<CmsArticleVo> transferList(List<CmsArticle>  cmsArticles) {
		List<CmsArticleVo> vos = new ArrayList<>();
		if (cmsArticles != null) {
			for (CmsArticle cmsArticle : cmsArticles) {
				vos.add(transfer(cmsArticle));
			}
		}
		
		return vos;
	}

}
