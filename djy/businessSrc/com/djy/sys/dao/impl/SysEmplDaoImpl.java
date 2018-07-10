package com.djy.sys.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.sys.dao.SysEmplDao;
import com.djy.sys.model.SysEmpl;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("sysEmplDao")
public class SysEmplDaoImpl extends BaseDaoImpl<SysEmpl> implements SysEmplDao{

	@Override
	public int updateSysEmplStatus(Integer[] ids, int status) {
		if(ids.length > 0){
			String hql = "UPDATE " + SysEmpl.class.getName() + " empl SET empl.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

	@Override
	public List<SysEmpl> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + SysEmpl.class.getName() + " empl WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String emplID = (String)param.get("emplID");
		if ( !StringUtil.isEmpty( emplID ) ) {
			hql += " AND empl.emplID LIKE :emplID";
			params.put("emplID", "%%" + emplID + "%%");
		}
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND empl.realName LIKE :realName";
			params.put("realName", "%%" + realName + "%%");
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND empl.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND empl.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND empl.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( empl.emplId LIKE :searchText"
				+ " OR empl.realName LIKE :searchText" 
				+ " OR empl.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY empl." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY empl.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
