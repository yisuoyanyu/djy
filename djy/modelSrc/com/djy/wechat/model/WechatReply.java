package com.djy.wechat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.frame.base.model.BaseModel;



@Entity
@Table(name = "wechat_reply")
@org.hibernate.annotations.Table(appliesTo = "wechat_reply", comment = "微信公众号自动回复")
public class WechatReply extends BaseModel {

	private String type;
	private String input;
	private String output;
	private Long count;
	private String description;
	private String picurl;
	private String url;

	
	@Column(
			name = "Type", 
			nullable = false, 
			length = 36, 
			columnDefinition = "VARCHAR(36) COMMENT '类型'" )
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	@Column(
			name = "Input", 
			nullable = false, 
			length = 72, 
			columnDefinition = "VARCHAR(72) COMMENT ''" )
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	
	
	@Column(
			name = "Output", 
			nullable = false, 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT ''" )
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}

	
	@Column(
			name = "Count", 
			columnDefinition = "BIGINT(36) COMMENT ''" )
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	
	@Column(
			name = "Description", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT ''" )
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(
			name = "Picurl", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT ''" )
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	
	@Column(
			name = "Url", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT ''" )
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
