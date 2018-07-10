package com.djy.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysEmplDao;
import com.djy.sys.enumtype.SysEmplStatus;
import com.djy.sys.model.SysEmpl;
import com.djy.sys.service.SysEmplService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("sysEmplService")
public class SysEmplServiceImpl extends BaseServiceImpl<SysEmpl> implements SysEmplService{
	
	@Autowired
	private SysEmplDao sysEmplDao;
	
	@Override
	public SysEmpl getByEmplID(String emplID) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("emplID", emplID);
		filter.addFilter("status", SysEmplStatus.normal.getId());
		return this.getByFilter(filter);
	}

	@Override
	public String encodePassword(SysEmpl sysEmpl, String password) {
		password = (new StringBuilder(String.valueOf(password))).append(sysEmpl.getId()).toString();
		for (int i = 0; i < 128 + sysEmpl.getId().intValue() % 128; i++)
			password = StringUtil.md5s(password);

		return password;
	}

	@Override
	public int delSysEmpl(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			SysEmpl sysEmpl = this.get(ids[i]);
			
			if (sysEmpl == null) continue;
			
			//删除员工信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", sysEmpl.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}

	@Override
	public int freezeSysEmpl(Integer[] ids) {
		
		return this.sysEmplDao.updateSysEmplStatus(ids, SysEmplStatus.freeze.getId());
	}

	@Override
	public int normalSysEmpl(Integer[] ids) {
		return this.sysEmplDao.updateSysEmplStatus(ids, SysEmplStatus.normal.getId());
	}

	@Override
	public List<SysEmpl> search(PagingBean pb, Map<String, Object> param) {
		return this.sysEmplDao.search(pb, param);
	}

	@Override
	public SysEmpl checkLogin(String account, String password) {
		SysEmpl sysEmpl = this.getByUsername(account);
		
		if (sysEmpl == null || sysEmpl.getStatus() == SysEmplStatus.freeze.getId())
			return null;
		
		if ( sysEmpl.getPassword().equals(encodePassword(sysEmpl, password)) )
			return sysEmpl;
		else
			return null;
	}

	private SysEmpl getByUsername(String emplID) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("emplID", emplID);
		return this.getByFilter(filter);
	}

	@Override
	public SysEmpl getSysEmplByMobile(String mobile) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("mobile", mobile);
		return this.getByFilter(hqlFilter);
	}


}
