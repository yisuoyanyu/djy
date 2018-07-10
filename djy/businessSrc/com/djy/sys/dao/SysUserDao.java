package com.djy.sys.dao;

import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysUser;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface SysUserDao extends BaseDao<SysUser> {

	int updateSysUserStatus(Integer[] ids, int status);

	List<SysUser> search(PagingBean pb, Map<String, Object> param);

	
}
