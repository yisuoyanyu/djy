package com.djy.coupon.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartner;
import com.djy.coupon.dao.ActCouponDao;
import com.djy.coupon.enumtype.ActCouponStatus;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.service.ActCouponService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("actCouponService")
public class ActCouponServiceImpl extends BaseServiceImpl<ActCoupon> implements ActCouponService{
	
	@Autowired
	private ActCouponDao actCouponDao;
	
	
	
	@Override
	public List<ActCoupon> search(PagingBean pb, Map<String, Object> param) {
		
		return actCouponDao.search(pb,param);
		
	}
	
	
	@Override
	public int delActCoupon(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			ActCoupon actCoupon = this.get(ids[i]);
			
			if (actCoupon == null) continue;
			
			//删除送券活动信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", actCoupon.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}
	
	
	@Override
	public int updateActCoupon(Integer[] ids, Integer status) {
		return this.actCouponDao.updateActCouponStatus(ids, status);
	}
	
	
	@Override
	public List<ActCoupon> getByCopartner(CoPartner coPartner) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("coPartner.id", coPartner.getId());
		hqlFilter.addFilter("status", ActCouponStatus.normal.getId());
		hqlFilter.addOrder("lastTime", true);
		return findByFilter(hqlFilter);
	}
	
	
	@Override
	public List<ActCoupon> getActsByPage(int page) {
		
		return this.actCouponDao.getActsByPage(page);
		
	}
	
	

}
