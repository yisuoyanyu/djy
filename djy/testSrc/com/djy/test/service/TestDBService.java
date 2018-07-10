package com.djy.test.service;

import com.djy.sys.model.SysUser;
import com.frame.base.service.BaseService;

public interface TestDBService extends BaseService<SysUser> {

	SysUser getByUsername(String username);
	
	//SysUser getByUsernameDao(String username);
	
}
