package com.djy.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysResourceDao;
import com.djy.sys.model.SysResource;
import com.djy.sys.service.SysResourceService;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("sysResourceService")
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService{
	
	@Autowired
	private SysResourceDao sysResourceDao;
	
	
	@Override
	public List<SysResource> getAllRes() {
		 return sysResourceDao.getAllRes();
	}

}
