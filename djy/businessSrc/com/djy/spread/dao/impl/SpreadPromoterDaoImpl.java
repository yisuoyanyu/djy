package com.djy.spread.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.spread.dao.SpreadPromoterDao;
import com.djy.spread.model.SpreadPromoter;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("spreadPromoterDao")
public class SpreadPromoterDaoImpl extends BaseDaoImpl<SpreadPromoter> implements SpreadPromoterDao{

	@Override
	public List<SpreadPromoter> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + SpreadPromoter.class.getName() + " spreadPromoter WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String emplID = (String)param.get("emplID");
		if ( !StringUtil.isEmpty( emplID ) ) {
			hql += " AND spreadPromoter.emplID LIKE :emplID";
			params.put("emplID", "%%" + emplID + "%%");
		}
		
		String realName = (String)param.get("realName");
		if ( !StringUtil.isEmpty( realName ) ) {
			hql += " AND spreadPromoter.realName LIKE :realName";
			params.put("realName", "%%" + realName + "%%");
		}
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND spreadPromoter.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND spreadPromoter.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND spreadPromoter.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND ( spreadPromoter.emplId LIKE :searchText"
				+ " OR spreadPromoter.realName LIKE :searchText" 
				+ " OR spreadPromoter.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY spreadPromoter." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY spreadPromoter.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public int updateSpreadPromoterStatus(Integer[] ids, int status) {
		if(ids.length > 0){
			String hql = "UPDATE " + SpreadPromoter.class.getName() + " spreadPromoter SET spreadPromoter.status=:status WHERE id IN (:ids)";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			params.put("ids", ids);
			
			return this.executeHql(hql, params);
		}else{
			return 0;
		}
	}

}
