package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.coupon.model.CouponDiscount;
import com.frame.base.web.vo.BaseVo;

public class CouponDiscountVo extends BaseVo {
	
	private Integer id;
	private String username;
	private String title;
	private String no;
	private String coPartnerName;
	private Double discountPercent;
	private Double useMinConsume;
	private Date useStartDate;
	private Date useEndDate;
	private String statusName;
	private Date usedTime;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCoPartnerName() {
		return coPartnerName;
	}

	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getUseMinConsume() {
		return useMinConsume;
	}

	public void setUseMinConsume(Double useMinConsume) {
		this.useMinConsume = useMinConsume;
	}

	public Date getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}

	public Date getUseEndDate() {
		return useEndDate;
	}

	public void setUseEndDate(Date useEndDate) {
		this.useEndDate = useEndDate;
	}
	

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	
	//----------------------------------------
	
	public static CouponDiscountVo transfer(CouponDiscount couponDiscount) {
		CouponDiscountVo vo = new CouponDiscountVo();
		vo.copyProperity( couponDiscount );
		
		vo.setUsername(couponDiscount.getUser().getWechatUser().getNickname());
		
		CoPartner coPartner = couponDiscount.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(coPartner.getName());
		}
		
		return vo;
	}
	
	public static List<CouponDiscountVo> transferList(List<CouponDiscount> couponDiscounts) {
		List<CouponDiscountVo> vos = new ArrayList<>();
		
		for ( CouponDiscount couponDiscount : couponDiscounts ) {
			vos.add( transfer( couponDiscount ) );
		}
		
		return vos;
	}
	
	
}
