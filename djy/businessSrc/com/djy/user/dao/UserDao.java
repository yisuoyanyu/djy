package com.djy.user.dao;

import java.util.List;
import java.util.Map;

import com.djy.user.model.User;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface UserDao extends BaseDao<User>{

	List<User> search(PagingBean pb, Map<String, Object> param);

	List<User> findReferPageByUser(PagingBean pb, User user);
	
	List<User> findMyspreadUser(User user, Integer page);

	Long getFirstConsumeUserNum(Map<String, Object> param);

	Long getFirstConsumeUserNumByCoParnerId(Map<String, Object> param);
}
