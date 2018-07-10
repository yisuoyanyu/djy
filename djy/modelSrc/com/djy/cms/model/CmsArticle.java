package com.djy.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.djy.cms.enumtype.CmsArticleStatus;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "cms_article")
@org.hibernate.annotations.Table(appliesTo="cms_article", comment="文章")
public class CmsArticle extends BaseModel {

	private CmsCatg cmsCatg;
	
	private String code;
	private String title;
	private String content;
	private Integer status;
	private Date insertTime; 
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
    		name = "cms_catg_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '内容分类ID'" )
	public CmsCatg getCmsCatg() {
		return cmsCatg;
	}
	public void setCmsCatg(CmsCatg cmsCatg) {
		this.cmsCatg = cmsCatg;
	}
	
	
	@Column(
			name = "Code", 
			nullable = false, 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '标识符'" )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Column(
			name = "Title", 
			nullable = false, 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "Content", 
			nullable = false, 
			columnDefinition = "MEDIUMTEXT COMMENT '图文内容'" )
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。0—禁用，1—正常。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return CmsArticleStatus.fromId(status).getValue();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "InsertTime", 
    		nullable = false, 
    		columnDefinition = "DATETIME COMMENT '插入时间'" )
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
