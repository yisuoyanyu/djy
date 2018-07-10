package com.djy.copartner.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.copartner.vo.CouponDiscountVo;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.CouponDiscountService;
import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/copartner/couponDiscount")
public class CouponDiscountController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(CouponDiscountController.class);
	
	@Autowired
	private CouponDiscountService couponDiscountService; 
	
	@RequestMapping("/noUserList.do")
	public String noUserList(HttpSession session, Model m) {
		
		return "/copartner/couponDiscount/noUserList";
		
	}
	
	/*获取个人无使用的打折券的列表*/
	@RequestMapping("/getNoCouponDiscount.do")
	@ResponseBody
	public List<CouponDiscountVo> getNoCouponDiscount(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		List<CouponDiscountVo> couponDiscountVos = new ArrayList<>();
		List<CouponDiscount> couponDiscounts = couponDiscountService.getNoCouponDiscount(userId,page);
		
		for(int i= 0;i<couponDiscounts.size();i++){
			CouponDiscount couponDiscount = couponDiscounts.get(i);
            CouponDiscountVo couponDiscountVo = CouponDiscountVo.getCouponDiscountvo(couponDiscount);
            couponDiscountVos.add(couponDiscountVo);
		}
		return couponDiscountVos;
		
	}
	
	@RequestMapping("/alreadyUserList.do")
	public String alreadyUserList(HttpSession session, Model m) {
		
		return "/copartner/couponDiscount/alreadyUserList";
		
	}
	
	/*获取个人已使用或者已过期的打折券的列表*/
	@RequestMapping("/getAlreadyCouponDiscount.do")
	@ResponseBody
	public List<CouponDiscountVo> getAlreadyCouponDiscount(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		List<CouponDiscountVo> couponDiscountVos = new ArrayList<>();
		List<CouponDiscount> couponDiscounts = couponDiscountService.getAlreadyCouponDiscount(userId,page);
		
		for(int i= 0;i<couponDiscounts.size();i++){
			CouponDiscount couponDiscount = couponDiscounts.get(i);
            CouponDiscountVo couponDiscountVo = CouponDiscountVo.getAlreadyCouponDiscountvo(couponDiscount);
            couponDiscountVos.add(couponDiscountVo);
		}
		return couponDiscountVos;
		
	}
}
