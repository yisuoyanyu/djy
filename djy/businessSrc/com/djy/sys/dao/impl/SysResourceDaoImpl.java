package com.djy.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysResourceDao;
import com.djy.sys.model.SysResource;
import com.frame.base.dao.impl.BaseDaoImpl;

@Service("sysResourceDao")
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource> implements SysResourceDao{

	@Override
	public List<SysResource> getAllRes() {
		 String hql = (new StringBuilder("FROM ")).append(SysResource.class.getName()).append(" WHERE 1=1 ").toString();
	        return this.find(hql);
	}

}
