package com.djy.sys.dao;

import java.util.List;

import com.djy.sys.model.SysResource;
import com.frame.base.dao.BaseDao;

public interface SysResourceDao extends BaseDao<SysResource>{

	List<SysResource> getAllRes();

}
