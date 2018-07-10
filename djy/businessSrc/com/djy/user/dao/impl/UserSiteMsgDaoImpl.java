package com.djy.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.user.dao.UserSiteMsgDao;
import com.djy.user.model.User;
import com.djy.user.model.UserSiteMsg;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("userSiteMsgDao")
public class UserSiteMsgDaoImpl extends BaseDaoImpl<UserSiteMsg> implements UserSiteMsgDao{

	@Override
	public List<UserSiteMsg> getByPageType(Integer page, Integer type,User user) {
		String hql = "";
		
		hql =" FROM " + UserSiteMsg.class.getName() + " usm  WHERE 1=1";
		hql += " AND usm.type = "+type;
		hql += " AND usm.user.id = "+user.getId();
	    hql += " ORDER BY usm.insertTime DESC";
	
		return this.find(hql, page, 5);
	}

	@Override
	public List<UserSiteMsg> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + UserSiteMsg.class.getName() + " userSiteMsg WHERE 1=1 ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer userId = (Integer)param.get("userId");
		if(!StringUtil.isEmpty( userId )){
			hql += " AND userSiteMsg.user.id =(:userId)";
			params.put("userId", userId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND userSiteMsg.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND userSiteMsg.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (userSiteMsg.content LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY userSiteMsg." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY userSiteMsg.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
