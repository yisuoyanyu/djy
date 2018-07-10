package com.djy.test.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.sys.model.SysUser;
import com.djy.test.dao.TestDBDao;
import com.frame.base.dao.impl.BaseDaoImpl;

//@Service("testDBDao")
public class TestDBDaoImpl extends BaseDaoImpl<SysUser> implements TestDBDao {

	@Override
	public SysUser getByUsername(String username) {
		String hql = "select distinct t from " + SysUser.class.getName() + " t where username = :username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "admin");
		SysUser sysUser = this.getByHql(hql, params);
		return sysUser;
	}
	
}
