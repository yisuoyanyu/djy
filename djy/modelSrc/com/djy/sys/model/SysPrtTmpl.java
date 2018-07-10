package com.djy.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.frame.base.model.BaseModel;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;

@Entity
@Table(name = "sys_prt_tmpl")
@org.hibernate.annotations.Table(appliesTo="sys_prt_tmpl", comment="套打模板")
public class SysPrtTmpl extends BaseModel {
	
	private String code;
	private String title;
	private String fileTitle;
	private String filePath;
	private Date insertTime;
	
	
	
	@Column(
			name = "Code", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '唯一标识符'" )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Column(
			name = "Title", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "FileTitle", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '模板文件标题'" )
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	
	
	@Column(
			name = "FilePath", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '模板文件相对路径'" )
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Transient
	public String getFileSrc() {
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		if ( StringUtil.isEmpty( fssApiSer ) ) {
			return filePath;
		} else {
			return fssApiSer + ConfigUtil.get("sys.fssClient.accessKeyId") + filePath;
		}
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
