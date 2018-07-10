package com.djy.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.djy.wechat.model.WechatMsgTmpl;
import com.djy.wechat.service.WechatMsgTmplService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;


@Service("wechatMsgTmplService")
public class WechatMsgTmplServiceImpl extends BaseServiceImpl<WechatMsgTmpl> implements WechatMsgTmplService {
	
	
	@Override
	public WechatMsgTmpl getByCode( String code ) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("code", code);
		return this.getByFilter( filter );
	}
	
	
	@Override
	public String getTemplateIdByCode( String code ) {
		WechatMsgTmpl wechatMsgTmpl = this.getByCode( code );
		return wechatMsgTmpl.getTemplateId();
	}
	
	
}
