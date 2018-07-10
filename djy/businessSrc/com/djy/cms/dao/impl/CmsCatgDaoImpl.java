package com.djy.cms.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.cms.dao.CmsCatgDao;
import com.djy.cms.model.CmsCatg;
import com.frame.base.dao.impl.BaseDaoImpl;

@Service("cmsCatgDao")
public class CmsCatgDaoImpl extends BaseDaoImpl<CmsCatg> implements CmsCatgDao{

	@Override
	public int deleteCmsCatg(Integer[] cmsCatgIds) {
		if(cmsCatgIds.length > 0){
			String hql = "DELETE " + CmsCatg.class.getName() + " catg WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", cmsCatgIds);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

	@Override
	public int updateCmsCatgStatus(Integer[] cmsCatgIds, int status) {
		if(cmsCatgIds.length > 0){
			String hql = "UPDATE " + CmsCatg.class.getName() + " catg SET catg.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", cmsCatgIds);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

}
