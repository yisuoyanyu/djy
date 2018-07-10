package com.djy.job.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.djy.coupon.dao.CouponDiscountDao;
import com.djy.coupon.model.CouponDiscount;
import com.djy.job.service.CouponDiscountQuartzService;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatTmplMsgService;

@Service("couponDiscountQuartzService")
public class CouponDiscountQuartzServiceImpl implements CouponDiscountQuartzService{

	private static Logger logger = LoggerFactory.getLogger(CouponDiscountQuartzServiceImpl.class);
	
	@Autowired
	private CouponDiscountDao couponDiscountDao;
	
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	@Override
	@Transactional
	public void couponDiscountQuartz() {

		logger.info("开始 生成查询抵用券排程……");
		
		try {
		List<CouponDiscount> couponDiscounts = couponDiscountDao.getDiscountsThanThreeDays();//获取到小于三天的打折券
			
	        for(int i = 0;i<couponDiscounts.size();i++){
				CouponDiscount couponDiscount = couponDiscounts.get(i);
				msgThanThreeDaysUser(couponDiscount);//发送模板消息
			}
	        
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
		}
	        logger.info("完成 生成查询抵用券排程");
	}
	
	
	private void msgThanThreeDaysUser(CouponDiscount couponDiscount) {
		
        WechatUser toUser = couponDiscount.getUser().getWechatUser();
		
		String tmplCode = "coEmplReceiptSuccess";//TODO：创建新的模板消息模板
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有尚未使用的优惠券马上到期！");
		
		// 门店名称
		data.put("keyword1", couponDiscount.getCoPartner().getName());
		
		data.put("remark", "请您马上使用！");
			
		try {	
			wechatTmplMsgService.sendTmplMsg(toUser, tmplCode, data);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	
}
