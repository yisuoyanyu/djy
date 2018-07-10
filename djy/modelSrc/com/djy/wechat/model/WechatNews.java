package com.djy.wechat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.frame.base.model.BaseModel;

@Entity
@Table(name = "wechat_news")
@org.hibernate.annotations.Table(appliesTo = "wechat_news", comment = "微信公众号图文消息")
public class WechatNews extends BaseModel {

	private int parentId;
	
	private String title;
	private String description;
	private String picurl;
	private String url;
	
	
	@Column(
			name = "ParentId", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '消息组ID'" )
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
	@Column(
			name = "Title", 
			nullable = false, 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '消息标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "Description", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '消息描述'" )
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(
			name = "Picurl", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '图片url地址'" )
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	
	
	@Column(
			name = "Url", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '消息url地址'" )
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	//------------------------------------------------------
	
	public WechatNews() {
		
	}
	
	public WechatNews(int parentId, String title, String description, String picurl, String url) {
		this.parentId = parentId;
		this.title = title;
		this.description = description;
		this.picurl = picurl;
		this.url = url;
	}
	
	
}
