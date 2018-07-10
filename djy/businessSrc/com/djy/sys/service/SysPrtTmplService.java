package com.djy.sys.service;

import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysPrtTmpl;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;


public interface SysPrtTmplService extends BaseService<SysPrtTmpl> {
	
	SysPrtTmpl getByCode(String code);
	
	// ------------------------------------------------------
	
	List<SysPrtTmpl> search(PagingBean pb, Map<String, Object> param);
	
	// ------------------------------------------------------
	
	int delSysPrtTmpl(Integer[] ids);
	
	void prtByTmpl(SysPrtTmpl sysPrtTmpl, Map<String, Object> data, String saveAs);
	
}
