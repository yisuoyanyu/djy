package com.djy.test.service;

import com.djy.sys.model.SysUser;

public interface TestThreadService {

	SysUser getByUsername(String username);
	
}
