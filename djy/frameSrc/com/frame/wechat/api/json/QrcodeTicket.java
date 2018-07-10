package com.frame.wechat.api.json;

import net.sf.json.JSONObject;

public class QrcodeTicket {
	
	private String ticket;
	private long expire_seconds;
	private String url;
	
	
	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
	public long getExpire_seconds() {
		return expire_seconds;
	}
	
	public void setExpire_seconds(long expire_seconds) {
		this.expire_seconds = expire_seconds;
	}
	
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public static QrcodeTicket fromJson(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		QrcodeTicket qrcodeTicket = (QrcodeTicket) JSONObject.toBean(jsonObject, QrcodeTicket.class);
		return qrcodeTicket;
	}
	
	
}
