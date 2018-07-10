package com.djy.sms.service;

import com.djy.sms.model.SmsTmpl;
import com.frame.base.service.BaseService;
import com.frame.sms.json.SmsTpl;

public interface SmsTmplService extends BaseService<SmsTmpl> {
	
	SmsTmpl getByCode( String code );
	
	SmsTpl getTplByCode( String code );
	
}
