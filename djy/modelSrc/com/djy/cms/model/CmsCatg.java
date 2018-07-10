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

import com.djy.cms.enumtype.CmsCatgStatus;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "cms_catg")
@org.hibernate.annotations.Table(appliesTo="cms_catg", comment="内容分类")
public class CmsCatg extends BaseModel {

	private CmsCatg parentCmsCatg;
	private String name;
	private Integer sortNumber;
	private Integer status;
	private Date insertTime;
	private Boolean isParent;
	private String idPath;
	private String parentIdPath;
	private String namePath;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "ParentId", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '父目录ID'" )
	public CmsCatg getParentCmsCatg() {
		return parentCmsCatg;
	}
	public void setParentCmsCatg(CmsCatg parentCmsCatg) {
		this.parentCmsCatg = parentCmsCatg;
	}
	
	
	@Column(
			name = "Name", 
			nullable = false, 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '目录名称'" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(
			name = "SortNumber", 
			columnDefinition = "INT(4) COMMENT '排序号'" )
	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
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
		return CmsCatgStatus.fromId(status).getValue();
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
	
	
	@Column(
			name = "IsParent", 
			columnDefinition = "TINYINT(1) COMMENT '（程序使用）是否父分类，1—父分类。'" )
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
	@Column(
			name = "IdPath", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '（程序使用）全ID路径，ID拼接的路径，以 / 分隔及结尾。'" )
	public String getIdPath() {
		return idPath;
	}
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}
	
	
	@Column(
			name = "ParentIdPath", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '（程序使用）父ID路径，ID拼接的路径，以 / 分隔及结尾。'" )
	public String getParentIdPath() {
		return parentIdPath;
	}
	public void setParentIdPath(String parentIdPath) {
		this.parentIdPath = parentIdPath;
	}
	
	
	@Column(
			name = "NamePath", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '（程序使用）全名称路径，名称拼接的路径，以 / 分隔。'" )
	public String getNamePath() {
		return namePath;
	}
	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}
	
	
}
