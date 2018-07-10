package com.djy.sys.service;

import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysUser;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface SysUserService extends BaseService<SysUser> {

	SysUser getByUsername(String username);
	
	SysUser getByMobile(String mobile);
	
	String encodePassword(SysUser sysUser, String password);
	
	SysUser checkLogin(String account, String password);

	int freezeSysUser(Integer[] ids);

	int normalSysUser(Integer[] ids);

	int delSysUser(Integer[] ids);

	List<SysUser> search(PagingBean pb, Map<String, Object> param);
	
}
