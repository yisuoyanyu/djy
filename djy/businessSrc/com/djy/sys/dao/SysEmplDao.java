package com.djy.sys.dao;

import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysEmpl;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface SysEmplDao extends BaseDao<SysEmpl>{

	int updateSysEmplStatus(Integer[] ids, int status);

	List<SysEmpl> search(PagingBean pb, Map<String, Object> param);

}
