package com.djy.sys.service;

import java.util.List;

import com.djy.sys.model.SysRole;
import com.frame.base.service.BaseService;


public interface SysRoleService extends BaseService<SysRole>{

	List<SysRole> findStoreRoles();
	
	SysRole getAgentRole();
	
	List<SysRole> findStaffRoles();

	SysRole getPartnerRole();
	
}
