package com.djy.test.dao;

import com.djy.sys.model.SysUser;
import com.frame.base.dao.BaseDao;

public interface TestDBDao extends BaseDao<SysUser> {
	
	SysUser getByUsername(String username);
	
}
