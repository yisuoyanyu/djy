package com.djy.coupon.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartner;
import com.djy.coupon.dao.CouponDiscountDao;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.user.model.User;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.vo.PagingBean;

@Service("couponDiscountService")
public class CouponDiscountServiceImpl extends BaseServiceImpl<CouponDiscount> implements CouponDiscountService{
	
	@Autowired
	private CouponDiscountDao couponDiscountDao;
	
	@Override
	public List<CouponDiscount> search(PagingBean pb, Map<String, Object> param) {
		return couponDiscountDao.search(pb,param);
	}

	@Override
	public long getNoUserCou(User user) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("status", CouponDiscountStatus.unused.getId());
		hqlFilter.addFilterFirstOr("UseEndDate", DateUtil.getFirstTimeOfDate(new Date()), SqlOperator.greaterEqual);
		hqlFilter.addFilterEndOr("UseEndDate", null, SqlOperator.equal);
		return this.countByFilter(hqlFilter);
	}
	
	

	@Override
	public CouponDiscount getCalculateDis(User user,Integer conpartnerId) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("coPartner.id", conpartnerId);
		hqlFilter.addFilter("status", CouponDiscountStatus.unused.getId());
		hqlFilter.addFilterFirstOr("UseEndDate", DateUtil.getFirstTimeOfDate(new Date()), SqlOperator.greaterEqual);
		hqlFilter.addFilterEndOr("UseEndDate", null, SqlOperator.equal);
		hqlFilter.addOrder("discountPercent", false);
		List<CouponDiscount> couponDiscounts = this.findByFilter(hqlFilter);
		if(couponDiscounts.size() > 0){
			return this.findByFilter(hqlFilter).get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public List<CouponDiscount> getNoCouponDiscount(Integer userId,int page) {
		
		return this.couponDiscountDao.getNoCouponDiscount(userId,page);
		
	}

	@Override
	public List<CouponDiscount> getAlreadyCouponDiscount(Integer userId, int page) {
		return this.couponDiscountDao.getAlreadyCouponDiscount(userId,page);
	}

	@Override
	public List<CouponDiscount> getCouByUserCop(User user, CoPartner coPartner,Double totalPrice) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("coPartner", coPartner);
		hqlFilter.addFilter("status", CouponDiscountStatus.unused.getId());
		hqlFilter.addFilterFirstOr("UseEndDate", DateUtil.getFirstTimeOfDate(new Date()), SqlOperator.greaterEqual);
		hqlFilter.addFilterEndOr("UseEndDate", null, SqlOperator.equal);
		return findByFilter(hqlFilter);
	}

	@Override
	public long getCouByUserCopNum(User user, CoPartner coPartner) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("coPartner", coPartner);
		hqlFilter.addFilter("status", CouponDiscountStatus.unused.getId());
		hqlFilter.addFilterFirstOr("UseEndDate", DateUtil.getFirstTimeOfDate(new Date()), SqlOperator.greaterEqual);
		hqlFilter.addFilterEndOr("UseEndDate", null, SqlOperator.equal);
		return countByFilter(hqlFilter);
	}

	@Override
	public Boolean ifGet(User user, ActCoupon actCoupon) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("actCoupon", actCoupon);
		hqlFilter.addFilter("status", CouponDiscountStatus.unused.getId(), SqlOperator.equal);//所有不是未使用的券，都是不能领取的;已占用状态可再次领取
		if (null == this.getByFilter(hqlFilter)) {
			return false;
		}else {
			return true;
		}
	}
	
}
