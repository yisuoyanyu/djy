package com.djy.sms.service.impl;

import org.springframework.stereotype.Service;

import com.djy.sms.model.SmsTmpl;
import com.djy.sms.service.SmsTmplService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.sms.json.SmsTpl;


@Service("smsTmplService")
public class SmsTmplServiceImpl extends BaseServiceImpl<SmsTmpl> implements SmsTmplService {
	
	
	@Override
	public SmsTmpl getByCode( String code ) {
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("code", code);
		return this.getByFilter( filter );
		
	}
	
	
	@Override
	public SmsTpl getTplByCode( String code ) {
		
		SmsTmpl smsTmpl = this.getByCode( code );
		
		SmsTpl tpl = new SmsTpl();
		tpl.setClTplCont( smsTmpl.getClTmplCont() );
		tpl.setJhTplId( smsTmpl.getJhTmplId() );
		
		return tpl;
		
	}
	
}
