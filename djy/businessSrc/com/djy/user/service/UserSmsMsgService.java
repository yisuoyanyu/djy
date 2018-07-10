package com.djy.user.service;

import java.util.List;
import java.util.Map;

import com.djy.user.model.UserSmsMsg;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;



public interface UserSmsMsgService extends BaseService<UserSmsMsg> {

	List<UserSmsMsg> search(PagingBean pb, Map<String, Object> param);

}
