package com.djy.user.service;

import com.djy.consume.model.ConsumeOrder;
import com.djy.user.model.User;
import com.djy.user.model.UserAccount;
import com.frame.base.service.BaseService;

public interface UserAccountService extends BaseService<UserAccount>{

	public void addUserAccount(User user);
	
	public void updateByPaySuccess(ConsumeOrder consumeOrder);
	
	public void addUserCanReceiveCoupons(User user);
}
