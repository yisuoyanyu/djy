package com.djy.copartner.vo;

import java.text.SimpleDateFormat;

import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.CouponDiscount;

public class CouponDiscountVo {

	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static CouponDiscountVo getCouponDiscountvo(CouponDiscount couponDiscount) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
		String useTime = "";
		if (null == couponDiscount.getUseEndDate()) {
			useTime = "无限制";
		}else {
			useTime= sf.format(couponDiscount.getUseEndDate());
		}
		
		String useMoney = "";
		if (null == couponDiscount.getUseMinConsume()) {
			useMoney = "&nbsp;";
		}else {
			useMoney = "满"+couponDiscount.getUseMinConsume().intValue()+"可用";
		}
		String info = "<li class=\"am-padding-xs am-padding-top-0\">"
			  +"<a href=\"javascript:copartnerDetail("+couponDiscount.getCoPartner().getId()+")\">" 
              +"<div class=\"am-padding-xs item\">"
              +"<img src=\"../copartner/img/ayhqback.png\" class=\"am-img-responsive\" style=\"width:100%;\" />"
		      +"<div class=\"c1\" style=\"height:20vw;width:20vw;\" align=\"center\">"
		      +"<img src=\"../copartner/img/dzq1.png\" class=\"voucher-img\" /></div>"
		      +"<div class=\"c2\" style=\"left:30%;\">"
			  +"<div>"+couponDiscount.getCoPartner().getName()+"</div></div>"
              +"<div class=\"am-text-xs end\">有效期："+useTime+"</div>"
              +"<div class=\"status\">"
              +" <div class=\"voucher-percent\">"+couponDiscount.getDiscountPercent()/10+"折</div>"
              +"<div class=\"voucher-totalprice\">"+useMoney+"</div>"
              +"<div class=\"voucher-get-div\">"
              +"<span class=\"voucher-get-span\">&nbsp;&nbsp;立即使用&nbsp;&nbsp;</span></div>"
              +"</div>"
              +"</div>"
              +"</a>"
              +"</li>";
		CouponDiscountVo couponDiscountVo = new CouponDiscountVo();
		couponDiscountVo.setInfo(info);
		return couponDiscountVo;
	}
	
	public static CouponDiscountVo getAlreadyCouponDiscountvo(CouponDiscount couponDiscount) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
		String useTime = "";
		if (null == couponDiscount.getUseEndDate()) {
			useTime = "无限制";
		}else {
			useTime= sf.format(couponDiscount.getUseEndDate());
		}
		
		String useMoney = "&nbsp;";
		if (null == couponDiscount.getUseMinConsume()) {
			useMoney = "";
		}else {
			useMoney = "满"+couponDiscount.getUseMinConsume().intValue()+"可用";
		}
		
		String state = "";
		if (couponDiscount.getStatus() == CouponDiscountStatus.used.getId()) {
			state = "已使用";
		}else if(couponDiscount.getStatus() == CouponDiscountStatus.occupy.getId()){
			state = "已占用";
		}else {
			state = "已过期";
		}
		String info = "<li class=\"am-padding-xs am-padding-top-0\">"
              +"<a href=\"javascript:void(0);\">"
              +"<div class=\"am-padding-xs item\">"
              +"<img src=\"../copartner/img/ayhqback.png\" class=\"am-img-responsive\" style=\"width:100%;\" />"
		      +"<div class=\"c1\" style=\"height:20vw;width:20vw;\" align=\"center\">"
		      +"<img src=\"../copartner/img/dzq1.png\" class=\"voucher-img\" /></div>"
		      +"<div class=\"c2\" style=\"left:30%;\">"
			  +"<div>"+couponDiscount.getCoPartner().getName()+"</div></div>"
              +"<div class=\"am-text-xs end\">有效期："+useTime+"</div>"
              +"<div class=\"status\">"
              +" <div class=\"voucher-percentlose\">"+couponDiscount.getDiscountPercent()/10+"折</div>"
              +"<div class=\"voucher-totalpricelose\">"+useMoney+"</div>"
              +"<div class=\"voucher-get-div\">"
              +"<span class=\"voucher-get-spanlose\">&nbsp;&nbsp;"+state+"&nbsp;&nbsp;</span></div>"
              +"</div>"
              +"</div>"
              +"</a>"
              +"</li>";
		CouponDiscountVo couponDiscountVo = new CouponDiscountVo();
		couponDiscountVo.setInfo(info);
		return couponDiscountVo;
	}
}
