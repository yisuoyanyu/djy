package com.djy.point.vo;

import com.frame.wechat.api.json.Sign;
import com.frame.wechat.config.WechatParameter;

public class WxConfigVo {
	private String appId;
	private long timestamp;
	private String nonceStr;
	private String signature;
	private String serverUrl;
	private String spreadUrl;
	
	
	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public String getNonceStr() {
		return nonceStr;
	}
	
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	
	public String getSpreadUrl() {
		return spreadUrl;
	}

	public void setSpreadUrl(String spreadUrl) {
		this.spreadUrl = spreadUrl;
	}
	
	
	public static WxConfigVo transfer(Sign sign) {
		WxConfigVo vo = new WxConfigVo();
		vo.setAppId(WechatParameter.appID);
		vo.setTimestamp(sign.getTimestamp());
		vo.setNonceStr(sign.getNoncestr());
		vo.setSignature(sign.getSignature());
		vo.setServerUrl(WechatParameter.serverUrl);
		return vo;
	}
}
