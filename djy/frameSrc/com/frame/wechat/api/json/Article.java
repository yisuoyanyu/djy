package com.frame.wechat.api.json;

import net.sf.json.JSONObject;

public class Article {
	
	private String title;
	private String description;
	private String url;
	private String picurl;
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public String getPicurl() {
		return picurl;
	}
	
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
	
	public static Article fromJson(String json) {
		Article article = (Article) JSONObject.toBean(JSONObject.fromObject(json), Article.class);
		return article;
	}
	
	public static JSONObject toJson(Article article) {
		return JSONObject.fromObject(article);
	}
	
}
