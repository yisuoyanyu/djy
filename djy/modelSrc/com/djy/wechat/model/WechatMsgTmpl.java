package com.djy.wechat.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.frame.base.model.BaseModel;


@Entity
@Table(name = "wechat_msg_tmpl")
@org.hibernate.annotations.Table(appliesTo = "wechat_msg_tmpl", comment = "微信公众号消息模板")
public class WechatMsgTmpl extends BaseModel {

	private String code;
	private String templateId;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
}
