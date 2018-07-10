package com.djy.copartner.vo;

import java.text.SimpleDateFormat;

import com.djy.coupon.model.ActCoupon;

public class ActCouponVo {

	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static ActCouponVo getActCouponVo(ActCoupon actCoupon,Boolean ifGet) {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
		String useTime = "";
		if (null == actCoupon.getUseEndDate()) {
			useTime = "有效期：无限制";
		}else {
			useTime= "有效期至："+sf.format(actCoupon.getUseEndDate());
		}
		
		String useMoney = "";
		if (null == actCoupon.getUseMinConsume()) {
			useMoney = "&nbsp;";
		}else {
			useMoney = "满"+actCoupon.getUseMinConsume()+"可用";
		}
		
		String getOrNo = "";
		String lastDiv = "";
		String herf = "";
		String zhang = "";
		if (ifGet) {
			zhang = "<i class=\"icon iconfont icon-yilingqu\" style=\"position: absolute;top: 13%;right: 32%;font-size: 13vw;\"></i>";
			getOrNo = "已领取";
			lastDiv =  " <div class=\"voucher-percentlose\">"+actCoupon.getDiscountPercent()/10+"折</div>"
		              +"<div class=\"voucher-totalpricelose\">"+useMoney+"</div>"
		              +"<div class=\"voucher-get-div\">"
		              +"<span class=\"voucher-get-spanlose\">&nbsp;&nbsp;"+getOrNo+"&nbsp;&nbsp;</span></div>";
		    herf = "<a href=\"javascript:void(0);\">";//
		}else {
			getOrNo = "立即领取";
			lastDiv =  " <div class=\"voucher-percent\">"+actCoupon.getDiscountPercent()/10+"折</div>"
		              +"<div class=\"voucher-totalprice\">"+useMoney+"</div>"
		              +"<div class=\"voucher-get-div\">"
		              +"<span class=\"voucher-get-span\">&nbsp;&nbsp;"+getOrNo+"&nbsp;&nbsp;</span></div>";
			herf = "<a href=\"javascript:getAct("+actCoupon.getId()+");\">";//领取;       
		}
		
		String info = "<li class=\"am-padding-xs am-padding-top-0\" style=\"margin-top: -0.5rem;\">"
              +herf
              +"<div class=\"am-padding-xs item\">"
              +"<img src=\"../copartner/img/ayhqback.png\" class=\"am-img-responsive\" style=\"width:100%;\" />"
		      +"<div class=\"c1\" style=\"height:20vw;width:20vw;\" align=\"center\">"
		      +"<img src=\"../copartner/img/dzq1.png\" class=\"voucher-img\" /></div>"
		      +"<div class=\"c2\" style=\"left:30%;\">"
			  +"<div>"+actCoupon.getCoPartner().getName()+"</div></div>"
              +"<div class=\"am-text-xs end\">"+useTime+"</div>"
              +zhang
              +"<div class=\"status\">"
              +lastDiv
              +"</div>"
              +"</div>"
              +"</a>"
              +"</li>";
		ActCouponVo actCouponVo = new ActCouponVo();
		actCouponVo.setInfo(info);
		return actCouponVo;
	}
}
