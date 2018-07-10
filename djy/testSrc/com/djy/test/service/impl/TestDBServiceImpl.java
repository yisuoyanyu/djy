package com.djy.test.service.impl;

import com.djy.sys.model.SysUser;
import com.djy.test.dao.TestDBDao;
import com.djy.test.service.TestDBService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testDBService")
public class TestDBServiceImpl extends BaseServiceImpl<SysUser> implements TestDBService {

	private static Logger logger = LoggerFactory.getLogger(TestDBServiceImpl.class);
	
//	@Autowired
//	private TestDBDao testDBDao;
	
	
	@Override
	public SysUser getByUsername(String username) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("username", username);
		return this.getByFilter(filter);
	}
	
//	@Override
//	public SysUser getByUsernameDao(String username) {
//		SysUser sysUser = testDBDao.getByUsername(username);
//		return sysUser;
//	}
	
}
