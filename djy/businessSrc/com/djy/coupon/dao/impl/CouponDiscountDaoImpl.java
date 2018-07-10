package com.djy.coupon.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.coupon.dao.CouponDiscountDao;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.CouponDiscount;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("couponDiscountDao")
public class CouponDiscountDaoImpl extends BaseDaoImpl<CouponDiscount> implements CouponDiscountDao{

	@Override
	public List<CouponDiscount> search(PagingBean pb, Map<String, Object> param) {
		String hql = " FROM " + CouponDiscount.class.getName() + " couponDiscount WHERE 1=1";
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String title = (String)param.get("title");
		if ( !StringUtil.isEmpty( title ) ) {
			hql += " AND couponDiscount.title LIKE :title";
			params.put("title", "%%" + title + "%%");
		}
		
		Integer userId = (Integer)param.get("userId");
		if(!StringUtil.isEmpty( userId )){
				
			hql += " AND couponDiscount.user.id = :id";
			params.put("id", userId);
		}
		
		String name = (String)param.get("coPartnerName");
		if(!StringUtil.isEmpty( name )){
				
			hql += " AND couponDiscount.coPartner.name LIKE :name";
			params.put("name", "%%" + name + "%%");
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND couponDiscount.usedTime >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND couponDiscount.usedTime <= :endTime";
			params.put("endTime", endTime);
		}
		
		String searchText = (String)param.get("searchText");
		if ( !StringUtil.isEmpty( searchText ) ) {
			hql += " AND (couponDiscount.title LIKE :searchText)";
			params.put("searchText", "%%" + searchText + "%%");
		}
		
		String sortName = (String)param.get("sortName");
		String sortOrder = (String)param.get("sortOrder");
		if ( !StringUtil.isEmpty( sortName ) && !StringUtil.isEmpty( sortOrder ) ) {
			hql += " ORDER BY couponDiscount." + sortName + " " + sortOrder;
		} else {
			hql += " ORDER BY couponDiscount.id DESC";
		}
		
		return this.findByHql(hql, params, pb);
	}

	@Override
	public List<CouponDiscount> getNoCouponDiscount(Integer userId,int page) {
        String hql = "";
		hql =" FROM " + CouponDiscount.class.getName() + " cou "+"WHERE 1=1";
        
		hql += " AND user.id = "+userId;
		hql += " AND status = "+CouponDiscountStatus.unused.getId();
		hql += " AND (UseEndDate is null or UseEndDate >= current_date())";//获取，没有时间限制或者时间限制在今天以后的结果
		return this.find(hql, page, 5);
	}

	@Override
	public List<CouponDiscount> getAlreadyCouponDiscount(Integer userId, int page) {
		String hql = "";
		hql =" FROM " + CouponDiscount.class.getName() + " cou "+"WHERE 1=1";
        
		hql += " AND user.id = "+userId;
		hql += " AND (status >= "+CouponDiscountStatus.occupy.getId()+" or UseEndDate < current_date())";
		return this.find(hql, page, 5);
	}

	@Override
	public List<CouponDiscount> getDiscountsThanThreeDays() {
		Date nowDate = new Date();
		Date afterDate = DateUtil.addDay(nowDate, 3);//获取三天以后的时间
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "";
		hql =" FROM " + CouponDiscount.class.getName() + " cou "+"WHERE 1=1";
        
		hql += " AND useEndDate != null";
		hql += " AND useEndDate <= :afterDate";
		hql += " AND status == 1";
		
		params.put("afterDate", afterDate);
		return this.find(hql, params);
	}
	
	
}
