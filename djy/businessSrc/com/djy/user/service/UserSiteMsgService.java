package com.djy.user.service;

import java.util.List;
import java.util.Map;

import com.djy.consume.model.ConsumeOrder;
import com.djy.coupon.model.CouponDiscount;
import com.djy.user.model.User;
import com.djy.user.model.UserSiteMsg;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface UserSiteMsgService extends BaseService<UserSiteMsg> {
	
	long countUnReadByUserId(User user,Integer type);
	
	long countAllUnReadByUserId(User user);
	
	UserSiteMsg getRecUnRed(User user,Integer type);
	
	List<UserSiteMsg> getByPageType(Integer page,Integer type,User user);

	List<UserSiteMsg> search(PagingBean pb, Map<String, Object> param);
	
	void addOrderMsg(ConsumeOrder consumeOrder);
	
	void addCoundiscount(CouponDiscount couponDiscount);
}
