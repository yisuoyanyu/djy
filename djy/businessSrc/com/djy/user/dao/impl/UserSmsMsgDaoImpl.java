package com.djy.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.user.dao.UserSmsMsgDao;
import com.djy.user.model.UserSmsMsg;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("userSmsMsgDao")
public class UserSmsMsgDaoImpl extends BaseDaoImpl<UserSmsMsg> implements UserSmsMsgDao{

	@Override
	public List<UserSmsMsg> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + UserSmsMsg.class.getName() + " userSmsMsg WHERE 1=1 ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Integer userId = (Integer)param.get("userId");
		if(!StringUtil.isEmpty( userId )){
			hql += " AND userSmsMsg.user.id =(:userId)";
			params.put("userId", userId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND userSmsMsg.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND userSmsMsg.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (userSmsMsg.content LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY userSmsMsg." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY userSmsMsg.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

}
