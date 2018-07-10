package com.djy.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysUserDao;
import com.djy.sys.enumtype.SysUserStatus;
import com.djy.sys.model.SysUser;
import com.djy.sys.service.SysUserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public SysUser getByUsername(String username) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("username", username);
		return this.getByFilter(filter);
	}
	
	@Override
	public SysUser getByMobile(String moblie) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("mobile", moblie);
		return this.getByFilter(filter);
	}
	
	@Override
	public String encodePassword(SysUser sysUser, String password) {
		password = (new StringBuilder(String.valueOf(password))).append(sysUser.getId()).toString();
		for (int i = 0; i < 128 + sysUser.getId().intValue() % 128; i++)
			password = StringUtil.md5s(password);

		return password;
	}
	
	@Override
	public SysUser checkLogin(String account, String password) {
		SysUser sysUser = this.getByUsername(account);
		
		if (sysUser == null)
			sysUser = this.getByMobile(account);
		
		if (sysUser == null || sysUser.getStatus() == SysUserStatus.freeze.getId())
			return null;
		
		if ( sysUser.getPassword().equals(encodePassword(sysUser, password)) )
			return sysUser;
		else
			return null;
	}
	
	//-----------------------------------------------------------------------------------------
	
	@Override
	public int freezeSysUser(Integer[] ids) {
		return this.sysUserDao.updateSysUserStatus(ids, SysUserStatus.freeze.getId());
	}

	@Override
	public int normalSysUser(Integer[] ids) {
		return this.sysUserDao.updateSysUserStatus(ids, SysUserStatus.normal.getId());
	}

	@Override
	public int delSysUser(Integer[] ids) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("id", ids, SqlOperator.in);
		return this.deleteByFilter(filter);
	}

	@Override
	public List<SysUser> search(PagingBean pb, Map<String, Object> param) {
		return this.sysUserDao.search(pb, param);
	}
	
}
