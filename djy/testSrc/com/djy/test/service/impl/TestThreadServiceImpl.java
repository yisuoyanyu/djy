package com.djy.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.djy.sys.model.SysUser;
import com.djy.sys.service.SysUserService;
import com.djy.test.service.TestThreadService;
import com.frame.base.dao.HqlFilter;

@Service("testThreadService")
public class TestThreadServiceImpl implements TestThreadService {

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	@Transactional
	public SysUser getByUsername(String username) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("username", username);
		return sysUserService.getByFilter(filter);
	}
	
}
