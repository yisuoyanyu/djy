package com.djy.sys.service;

import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysEmpl;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface SysEmplService extends BaseService<SysEmpl>{

	SysEmpl getByEmplID(String emplID);

	String encodePassword(SysEmpl sysEmpl, String password);

	int delSysEmpl(Integer[] ids);

	int freezeSysEmpl(Integer[] ids);

	int normalSysEmpl(Integer[] ids);

	List<SysEmpl> search(PagingBean pb, Map<String, Object> param);

	SysEmpl checkLogin(String account, String password);

	SysEmpl getSysEmplByMobile(String mobile);

}
