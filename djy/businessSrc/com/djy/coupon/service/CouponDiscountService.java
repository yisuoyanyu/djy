package com.djy.coupon.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.model.CouponDiscount;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CouponDiscountService extends BaseService<CouponDiscount>{

	List<CouponDiscount> search(PagingBean pb, Map<String, Object> param);

	long getNoUserCou(User user);
	
	List<CouponDiscount> getNoCouponDiscount(Integer userId,int page);
	
	List<CouponDiscount> getAlreadyCouponDiscount(Integer userId,int page);
	
	List<CouponDiscount> getCouByUserCop(User user,CoPartner coPartner,Double totalPrice);
	
	long getCouByUserCopNum(User user,CoPartner coPartner);
	
	Boolean ifGet(User user,ActCoupon actCoupon);
	
	CouponDiscount getCalculateDis(User user,Integer conpartnerId);
}
