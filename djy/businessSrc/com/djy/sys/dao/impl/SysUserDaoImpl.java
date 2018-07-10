package com.djy.sys.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysUserDao;
import com.djy.sys.model.SysUser;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("sysUserDao")
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

	@Override
	public int updateSysUserStatus(Integer[] ids, int status) {
		String hql = "UPDATE " + SysUser.class.getName() + " u SET u.status=:status WHERE id IN (:ids)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("ids", ids);
		
		return this.executeHql(hql, params);
	}

	@Override
	public List<SysUser> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + SysUser.class.getName() + " u WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String username = (String)param.get("username");
		if ( !StringUtil.isEmpty( username ) ) {
			hql += " AND u.username LIKE :username";
			params.put("username", "%%" + username + "%%");
		}
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND u.realName LIKE :realName";
			params.put("realName", "%%" + realName + "%%");
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND u.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND u.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND u.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( u.username LIKE :searchText"
				+ " OR u.realName LIKE :searchText" 
				+ " OR u.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY u." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY u.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}
	
}
