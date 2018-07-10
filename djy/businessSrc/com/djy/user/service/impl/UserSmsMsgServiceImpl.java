package com.djy.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.user.dao.UserSmsMsgDao;
import com.djy.user.model.UserSmsMsg;
import com.djy.user.service.UserSmsMsgService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("userSmsMsgService")
public class UserSmsMsgServiceImpl extends BaseServiceImpl<UserSmsMsg> implements UserSmsMsgService {
	
	@Autowired
	private UserSmsMsgDao userSmsMsgDao;
	
	@Override
	public List<UserSmsMsg> search(PagingBean pb, Map<String, Object> param) {
		return userSmsMsgDao.search(pb,param);
	}
	
	
	
}
