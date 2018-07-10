package com.djy.coupon.dao;

import java.util.List;
import java.util.Map;

import com.djy.coupon.model.CouponDiscount;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CouponDiscountDao extends BaseDao<CouponDiscount>{

	List<CouponDiscount> search(PagingBean pb, Map<String, Object> param);

	List<CouponDiscount> getNoCouponDiscount(Integer userId,int page);
	
	List<CouponDiscount> getAlreadyCouponDiscount(Integer userId,int page);
	
	List<CouponDiscount> getDiscountsThanThreeDays();
}
