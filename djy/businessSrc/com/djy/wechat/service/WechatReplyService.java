package com.djy.wechat.service;

import com.djy.wechat.model.WechatReply;
import com.frame.base.service.BaseService;

public interface WechatReplyService extends BaseService<WechatReply>{
	
	WechatReply getByContent(String content);
	
}
