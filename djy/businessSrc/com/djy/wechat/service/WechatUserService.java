package com.djy.wechat.service;

import java.util.Date;

import com.djy.wechat.model.WechatUser;
import com.frame.base.service.BaseService;
import com.frame.wechat.api.json.UserInfo;

public interface WechatUserService extends BaseService<WechatUser> {
	
	WechatUser refreshByUserInfo(WechatUser wechatUser, UserInfo userInfo, String sceneStr);
	
	WechatUser createByUserInfo(UserInfo userInfo, String sceneStr);
	
	Boolean isBind(WechatUser wechatUser);

	WechatUser getByOpenID(String OpenID);

	WechatUser getByUserId(Integer userId);
	
	Long getUserNumToday(Date startDate,Date endDate);

	Integer getUserNumAll();
	
}
