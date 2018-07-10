package com.frame.wechat.api.json;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class JsApiTicket implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ticket;		// Ʊ��
	private long expires_in;	// ��Чʱ�� ��λ
	private long createTime;	// ����ʱ�� ��λ����
	
	
	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
	public long getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	
	
	public long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
	public static JsApiTicket fromJson(String json) {
		JsApiTicket jsApiTicket = (JsApiTicket) JSONObject.toBean(JSONObject.fromObject(json), JsApiTicket.class);
		jsApiTicket.setCreateTime(System.currentTimeMillis());
		return jsApiTicket;
	}
}
