package com.djy.co.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerEmplDao;
import com.djy.co.enumtype.CoPartnerEmplStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.service.CoPartnerEmplService;
import com.djy.sys.enumtype.SysEmplStatus;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerStaffService")
public class CoPartnerEmplServiceImpl extends BaseServiceImpl<CoPartnerEmpl> implements CoPartnerEmplService{
	
	@Autowired
	private CoPartnerEmplDao coPartnerEmplDao;
	
	@Override
	public CoPartnerEmpl getByEmplID(String emplID) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("emplID", emplID);
		filter.addFilter("status", SysEmplStatus.normal.getId());
		return this.getByFilter(filter);
	}

	@Override
	public List<CoPartnerEmpl> search(PagingBean pb, Map<String, Object> param) {
		return this.coPartnerEmplDao.search(pb, param);
	}
	
	@Override
	public int delCoPartnerEmpl(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CoPartnerEmpl coPartnerEmpl = this.get(ids[i]);
			
			if (coPartnerEmpl == null) continue;
			
			//删除员工信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", coPartnerEmpl.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}
	
	@Override
	public int freezeCoPartnerEmpl(Integer[] ids) {
		return this.coPartnerEmplDao.updateCoPartnerEmplStatus(ids, CoPartnerEmplStatus.freeze.getId());
	}

	@Override
	public int normalCoPartnerEmpl(Integer[] ids) {
		return this.coPartnerEmplDao.updateCoPartnerEmplStatus(ids, CoPartnerEmplStatus.normal.getId());
	}

	@Override
	public List<CoPartnerEmpl> getAvailableEmpl(CoPartner coPartner) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("coPartner", coPartner);
		hqlFilter.addFilter("status", CoPartnerEmplStatus.normal.getId());
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
	}

}
