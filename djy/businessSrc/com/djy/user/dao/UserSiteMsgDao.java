package com.djy.user.dao;

import java.util.List;
import java.util.Map;

import com.djy.user.model.User;
import com.djy.user.model.UserSiteMsg;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface UserSiteMsgDao extends BaseDao<UserSiteMsg>{

	List<UserSiteMsg> getByPageType(Integer page,Integer type,User user);

	List<UserSiteMsg> search(PagingBean pb, Map<String, Object> param);
}
