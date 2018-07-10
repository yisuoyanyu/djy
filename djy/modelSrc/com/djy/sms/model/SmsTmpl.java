package com.djy.sms.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.frame.base.model.BaseModel;


@Entity
@Table(name = "sms_tmpl")
@org.hibernate.annotations.Table(appliesTo = "sms_tmpl", comment = "短信模板")
public class SmsTmpl extends BaseModel {

	private String code;
	private String clTmplCont;
	private String jhTmplId;
	
	
	
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
			name = "ClTmplCont", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '创蓝 模板内容'" )
	public String getClTmplCont() {
		return clTmplCont;
	}
	public void setClTmplCont(String clTmplCont) {
		this.clTmplCont = clTmplCont;
	}
	
	
	@Column(
			name = "JhTmplId", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '聚合 模板id'" )
	public String getJhTmplId() {
		return jhTmplId;
	}
	public void setJhTmplId(String jhTmplId) {
		this.jhTmplId = jhTmplId;
	}
	
	
	
}
