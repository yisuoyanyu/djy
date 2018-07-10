package com.djy.user.service.impl;

import org.springframework.stereotype.Service;

import com.djy.consume.model.ConsumeOrder;
import com.djy.user.model.User;
import com.djy.user.model.UserAccount;
import com.djy.user.service.UserAccountService;
import com.frame.base.service.impl.BaseServiceImpl;


@Service("userAccountService")
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount> implements UserAccountService {
	
	
	@Override
	public void addUserAccount(User user) {
		UserAccount userAccount = new UserAccount();
		userAccount.setUser( user );
		userAccount.setTotalConcume( 0d );
		userAccount.setTotalPayConcume( 0d );
		userAccount.setCanReceiveCoupons( 0 );
		userAccount.setCanReceiveCoupons(1);//首次关注赠送一次领券机会
		this.save( userAccount );
	}
	

	@Override
	public void updateByPaySuccess(ConsumeOrder consumeOrder) {
		UserAccount userAccount = consumeOrder.getUser().getUserAccount();
		
		Double consumeAmount = consumeOrder.getConsumeAmount();
		
		Double payAmount = consumeOrder.getPayAmount();
		
		String hql = "UPDATE " + UserAccount.class.getName() + " account SET "
				+ " account.totalConcume=account.totalConcume+" + consumeAmount
				+ " , account.totalPayConcume=account.totalPayConcume+" + payAmount
				+ " WHERE account.id=" + userAccount.getId();
		this.executeHql(hql);
		
	}


	@Override
	public void addUserCanReceiveCoupons(User user) {
	   UserAccount userAccount = user.getUserAccount();
	   String hql = "UPDATE " + UserAccount.class.getName() + " account SET "
				+ " account.canReceiveCoupons=account.canReceiveCoupons+" + 1
				+ " WHERE account.id=" + userAccount.getId();
		this.executeHql(hql);
	}
	
	

	
}
