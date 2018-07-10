package com.djy.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.user.dao.UserDao;
import com.djy.user.model.User;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
	
	@Override
	public List<User> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + User.class.getName() + " user WHERE 1=1 ";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String nickname = (String)param.get("username");
		if ( !StringUtil.isEmpty( nickname ) ) {
			hql += " AND user.wechatUser.nickname LIKE :nickname";
			params.put("nickname", "%%" + nickname + "%%");
		}
		
		
		String mobile = (String)param.get("mobile");
		if ( !StringUtil.isEmpty( mobile ) ) {
			hql += " AND user.mobile LIKE :mobile";
			params.put("mobile", "%%" + mobile + "%%");
		}
		
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND user.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND user.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (user.wechatUser.nickname LIKE :searchText"
					+ " OR user.mobile LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY user." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY user.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public List<User> findReferPageByUser(PagingBean pb, User user) {
		String hql = "";
	    hql ="select user FROM " + User.class.getName() + " user "+"WHERE 1=1";
	    hql += " AND (sponsor.id = :userId";
        hql += " or sponsor.sponsor.id = :userId)";
        
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		
		return this.find(hql, params, pb.getToPage(),pb.getPageSize());
	}
	
	@Override
	public List<User> findMyspreadUser(User user, Integer page) {
		String hql = "";
	    hql ="select user FROM " + User.class.getName() + " user "+"WHERE 1=1";
	    hql += " AND sponsor.id = :userId";
	    hql += " ORDER BY insertTime DESC";
        
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		
		return this.find(hql, params, page,10);
	}

	@Override
	public Long getFirstConsumeUserNum(Map<String, Object> param) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql ="SELECT COUNT(*) FROM " + User.class.getName() +" user WHERE user.sponsor IS NOT NULL ";
	    
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND user.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND user.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		return (Long) this.getUniqueByHql(hql, params);
	}

	@Override
	public Long getFirstConsumeUserNumByCoParnerId(Map<String, Object> param) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql ="SELECT COUNT(*) FROM " + User.class.getName() +" user WHERE 1=1 ";
	    
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND user.insertTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND user.insertTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		Integer coPartnerId = (Integer)param.get("coPartnerId");
		if ( !StringUtil.isEmpty( coPartnerId ) ) {
			hql += " AND user.ofCoPartner.id = :coPartnerId";
			params.put("coPartnerId", coPartnerId);
		}
		
		return (Long) this.getUniqueByHql(hql, params);
	}

}
