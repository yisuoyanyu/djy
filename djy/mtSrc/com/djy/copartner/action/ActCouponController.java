package com.djy.copartner.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.copartner.vo.ActCouponVo;
import com.djy.copartner.vo.CopartnerAdVo;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.ActCoupon;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.ActCouponService;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.point.vo.WxConfigVo;
import com.djy.user.model.User;
import com.djy.user.model.UserAccount;
import com.djy.user.service.UserAccountService;
import com.djy.user.service.UserService;
import com.djy.user.service.UserSiteMsgService;
import com.djy.utils.enumtype.SnPrefix;
import com.frame.base.utils.SnGenerator;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/copartner/actCoupon")
public class ActCouponController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(ActCouponController.class);
	
	@Autowired
	private ActCouponService actCouponService; 
	@Autowired
	private CouponDiscountService couponDiscountService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
    private HttpServletRequest request;
	@Autowired
	private UserSiteMsgService userSiteMsgService;
	
	@RequestMapping("/actCouponList.do")
	public String actCouponList(HttpSession session, Model m) {
		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
		m.addAttribute("wxConfig", wxConfig);
		return "/copartner/actCoupon/actCouponList";
		
	}
	
	/*获取活动列表*/
	@RequestMapping("/getActCoupons.do")
	@ResponseBody
	public List<ActCouponVo> getActCoupons(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
      
		List<ActCoupon> actCoupons = actCouponService.getActsByPage(page);
		
		List<ActCouponVo> actCouponVos = new ArrayList<>();
		
		for(int i = 0;i<actCoupons.size();i++){
			ActCoupon actCoupon = actCoupons.get(i);
			Boolean ifGet = couponDiscountService.ifGet(user, actCoupon);
			ActCouponVo actCouponVo = ActCouponVo.getActCouponVo(actCoupon,ifGet);
			actCouponVos.add(actCouponVo);
		}
		return actCouponVos;
		
	}
	
	@RequestMapping("/getAct.do")
	@ResponseBody
	public Result getAct(
			@RequestParam("actId") Integer actId,
			HttpSession session,Model m){
	
		ActCoupon actCoupon = actCouponService.get(actId);
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
		
		try {
			if (user.getUserAccount().getCanReceiveCoupons() > 0) {
				CouponDiscount couponDiscount = new CouponDiscount();
	            couponDiscount.setActCoupon(actCoupon);
	            couponDiscount.setUser(user);
	            couponDiscount.setTitle(actCoupon.getTitle());
	            couponDiscount.setNo(SnGenerator.generate( SnPrefix.couponDiscountNo.getValue() )  );
	            couponDiscount.setCoPartner(actCoupon.getCoPartner());
	            couponDiscount.setDiscountPercent(actCoupon.getDiscountPercent());
	            if (null != actCoupon.getUseMinConsume()) {
	            	couponDiscount.setUseMinConsume(actCoupon.getUseMinConsume());
				}
	            if (null != actCoupon.getUseStartDate()) {
	            	couponDiscount.setUseStartDate(actCoupon.getUseStartDate());
				}
	            if (null != actCoupon.getUseEndDate()) {
	            	couponDiscount.setUsedTime(actCoupon.getUseEndDate());
				}
	            couponDiscount.setStatus(CouponDiscountStatus.unused.getId());
	            couponDiscount.setInsertTime(new Date());
	            couponDiscountService.save(couponDiscount);
	            
	            //给用户发送优惠券的站内消息
	            userSiteMsgService.addCoundiscount(couponDiscount);
	            
	            UserAccount userAccount = user.getUserAccount();
	            userAccount.setCanReceiveCoupons(userAccount.getCanReceiveCoupons()-1);
	            userAccountService.update(userAccount);
	            
	            Integer rewardNum = 0;
	            if (null != actCoupon.getRewardNum()) {
					rewardNum = actCoupon.getRewardNum();
				}
	            actCoupon.setRewardNum(rewardNum+1);//增加其送券次数
	            actCouponService.update(actCoupon);
				return new Result(true, "领取成功");
				
			}else {
				return new Result(false, "您没有领券机会，请到分享中心进行分享，获取领券机会！");
			}
            
		} catch (Exception e) {
			if (null == user) {
				return new Result(false, "请您登入油惠站系统进行操作！");	
			}else {
				return new Result(false, "系统错误，请联系管理员！");	
			}
		}
	}
}
