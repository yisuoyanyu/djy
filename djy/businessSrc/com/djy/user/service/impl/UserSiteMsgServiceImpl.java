package com.djy.user.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.consume.model.ConsumeOrder;
import com.djy.coupon.model.CouponDiscount;
import com.djy.user.dao.UserSiteMsgDao;
import com.djy.user.enumtype.MessageType;
import com.djy.user.model.User;
import com.djy.user.model.UserSiteMsg;
import com.djy.user.service.UserSiteMsgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("userSiteMsgService")
public class UserSiteMsgServiceImpl extends BaseServiceImpl<UserSiteMsg> implements UserSiteMsgService {

	@Autowired
	private UserSiteMsgDao userSiteMsgDao;
	@Override
	public long countUnReadByUserId(User user, Integer type) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("user", user);
		filter.addFilter("type", type);
		filter.addFilter("readTime", null);
		long count = this.countByFilter(filter);
		return count;
	}
	
	

	@Override
	public long countAllUnReadByUserId(User user) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("user", user);
		filter.addFilter("readTime", null);
		long count = this.countByFilter(filter);
		return count;
	}



	@Override
	public UserSiteMsg getRecUnRed(User user, Integer type) {
		PagingBean pb = new PagingBean(0, 1);
		HqlFilter filter = new HqlFilter();
		filter.addFilter("user", user);
		filter.addFilter("type", type);
		filter.addFilter("readTime", null);
		filter.addOrder("insertTime", true);
		List<UserSiteMsg> userSiteMsgs = findByFilter(filter, pb);
		if (userSiteMsgs.size() > 0) {
			return userSiteMsgs.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public List<UserSiteMsg> getByPageType(Integer page, Integer type,User user) {
		return this.userSiteMsgDao.getByPageType(page, type,user);
	}

	@Override
	public List<UserSiteMsg> search(PagingBean pb, Map<String, Object> param) {
		return userSiteMsgDao.search(pb,param);
	}

	@Override
	public void addOrderMsg(ConsumeOrder consumeOrder) {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		String sucTime = sf.format(consumeOrder.getInsertTime());
		String copartnerName = consumeOrder.getCoPartner().getName();
		String couAmount = consumeOrder.getConsumeAmount()+"元";
		BigDecimal bg = new BigDecimal(consumeOrder.getConsumeAmount()-consumeOrder.getPayAmount());
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		String saveAmount = f1+"元";
		
		UserSiteMsg userSiteMsg = new UserSiteMsg();
		userSiteMsg.setConsumeOrder(consumeOrder);
//		if (null != consumeOrder.getCouponDiscount()) {//在使用打折券的情况下
//			userSiteMsg.setCouponDiscount(consumeOrder.getCouponDiscount());
//		}
		userSiteMsg.setInsertTime(new Date());
		userSiteMsg.setType(MessageType.order.getId());//设置为订单类型状态
		userSiteMsg.setUser(consumeOrder.getUser());
		userSiteMsg.setContent("尊敬的用户，您于"+sucTime+",在"+copartnerName+"成功加油，油额值"+couAmount+";此单油惠站帮您节约"+saveAmount+",欢迎您下次使用！");
	    this.save(userSiteMsg);
	}

	@Override
	public void addCoundiscount(CouponDiscount couponDiscount) {
		String conName = couponDiscount.getCoPartner().getName();
		String discount = new java.text.DecimalFormat("#.0").format(couponDiscount.getDiscountPercent()/10);
		
		UserSiteMsg userSiteMsg = new UserSiteMsg();
		userSiteMsg.setCouponDiscount(couponDiscount);
	    userSiteMsg.setInsertTime(new Date());
		userSiteMsg.setType(MessageType.coudiscount.getId());
		userSiteMsg.setUser(couponDiscount.getUser());
	    userSiteMsg.setContent("您已成功获得一张"+conName+discount+"折券，欢迎您前去加油，享受优惠！");
		this.save(userSiteMsg);
	}
	
	
	
}
