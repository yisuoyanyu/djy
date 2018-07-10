package com.djy.coupon.dao;

import java.util.List;
import java.util.Map;

import com.djy.coupon.model.ActCoupon;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface ActCouponDao extends BaseDao<ActCoupon>{

	List<ActCoupon> search(PagingBean pb, Map<String, Object> param);

	int updateActCouponStatus(Integer[] ids, Integer status);

	List<ActCoupon> getActsByPage(int page);
}
