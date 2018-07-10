package com.djy.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.djy.sys.model.SysRole;
import com.djy.sys.service.SysRoleService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService{

	
	@Override
	public List<SysRole> findStoreRoles() {
		HqlFilter filter = new HqlFilter();
		String[] codes = {"shopowner", "clerk"};
		filter.addFilter("code", codes, SqlOperator.in);
		return this.findByFilter( filter );
	}
	
	
	@Override
	public SysRole getAgentRole() {
		return this.getByFilter("code", "agent");
	}
	
	@Override
	public SysRole getPartnerRole() {
		return this.getByFilter("code", "partner");
	}
	
	@Override
	public List<SysRole> findStaffRoles() {
		HqlFilter filter = new HqlFilter();
		String[] codes = {"agent", "shopowner", "clerk"};
		filter.addFilter("code", codes, SqlOperator.notIn);
		return this.findByFilter( filter );
	}


	
	
	
}
