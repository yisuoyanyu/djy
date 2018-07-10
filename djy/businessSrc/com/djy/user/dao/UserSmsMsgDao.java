package com.djy.user.dao;

import java.util.List;
import java.util.Map;

import com.djy.user.model.UserSmsMsg;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface UserSmsMsgDao extends BaseDao<UserSmsMsg>{

	List<UserSmsMsg> search(PagingBean pb, Map<String, Object> param);

}
