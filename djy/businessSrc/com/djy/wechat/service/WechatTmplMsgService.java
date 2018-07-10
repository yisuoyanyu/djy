package com.djy.wechat.service;

import java.util.Map;

import com.djy.wechat.model.WechatTmplMsg;
import com.djy.wechat.model.WechatUser;
import com.frame.base.service.BaseService;

public interface WechatTmplMsgService extends BaseService<WechatTmplMsg> {
	
	WechatTmplMsg getByMsgId(String toUser, String msgId);
	
	WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content);
	
	WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content, String url);
	
	WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content, String miniProgramAppid, String miniProgramPagePath);
	
}
