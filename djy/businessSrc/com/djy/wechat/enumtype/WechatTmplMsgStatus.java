package com.djy.wechat.enumtype;


public enum WechatTmplMsgStatus {
	
	sending(1, "已发送"), 
	sendSuccess(2, "发送成功"), 
	sendFail(3, "发送失败"),
	sendFailed(4, "送达但失败");
	
	private int id;
	private String name;

	private WechatTmplMsgStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static WechatTmplMsgStatus fromId(int id) {
		for (WechatTmplMsgStatus ct : WechatTmplMsgStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static WechatTmplMsgStatus fromValue(String value) {
		for (WechatTmplMsgStatus ct : WechatTmplMsgStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
	
}
