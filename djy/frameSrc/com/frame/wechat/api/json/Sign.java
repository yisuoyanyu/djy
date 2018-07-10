package com.frame.wechat.api.json;

public class Sign {
	
	private String noncestr;
	private JsApiTicket jsApiTicket;
	private String url;
	private long timestamp;
	private String signature;
	
	
	public String getNoncestr() {
		return noncestr;
	}
	
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	
	
	public JsApiTicket getJsApiTicket() {
		return jsApiTicket;
	}
	
	public void setJsApiTicket(JsApiTicket jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}
	
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
