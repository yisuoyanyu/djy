package com.djy.wechat.service;

import com.djy.wechat.model.WechatMsgTmpl;
import com.frame.base.service.BaseService;

public interface WechatMsgTmplService extends BaseService<WechatMsgTmpl> {
	
	WechatMsgTmpl getByCode( String code );
	
	String getTemplateIdByCode( String code );
	
}
