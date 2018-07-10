package com.frame.wechat.api.json;

import net.sf.json.JSONObject;

public class TmplMsg {

	private Integer errcode;
	private String errmsg;
	private String msgid;
	
	
	
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	
	
	public static TmplMsg fromJson(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		TmplMsg tmplMsg = (TmplMsg) JSONObject.toBean(jsonObject, TmplMsg.class);
		return tmplMsg;
	}
	
	
}
