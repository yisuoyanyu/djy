package com.djy.coupon.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.coupon.model.ActCoupon;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface ActCouponService extends BaseService<ActCoupon>{

	List<ActCoupon> search(PagingBean pb, Map<String, Object> param);

	int delActCoupon(Integer[] ids);

	int updateActCoupon(Integer[] ids, Integer status);

	List<ActCoupon> getByCopartner(CoPartner coPartner);
	
	List<ActCoupon> getActsByPage(int page);
}
