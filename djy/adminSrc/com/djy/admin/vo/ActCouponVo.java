package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.coupon.enumtype.CouponType;
import com.djy.coupon.model.ActCoupon;
import com.frame.base.web.vo.BaseVo;

public class ActCouponVo extends BaseVo{
	private Integer id;
	private String title;
	private String coPartnerName;
	private Date startTime;					
	private Date endTime;		
	private Integer scene;
	private Integer type;
	private Double discountPercent;
	private Double givePointPercent;
	private Double useMinConsume;
	private Integer useDateType;
	private Date useStartDate;
	private Date UseEndDate;
	private Integer useDays;
	private Boolean isChoice;
	private Integer status;
	private Date insertTime;
	private Date lastTime;
	private Integer rewardNum;
	
	private String sceneName;
	private String typeName;
	private String userDateTypeName;
	private String statusName;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoPartnerName() {
		return coPartnerName;
	}

	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getGivePointPercent() {
		return givePointPercent;
	}

	public void setGivePointPercent(Double givePointPercent) {
		this.givePointPercent = givePointPercent;
	}

	public Double getUseMinConsume() {
		return useMinConsume;
	}

	public void setUseMinConsume(Double useMinConsume) {
		this.useMinConsume = useMinConsume;
	}

	public Integer getUseDateType() {
		return useDateType;
	}

	public void setUseDateType(Integer useDateType) {
		this.useDateType = useDateType;
	}

	public Date getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}

	public Date getUseEndDate() {
		return UseEndDate;
	}

	public void setUseEndDate(Date useEndDate) {
		UseEndDate = useEndDate;
	}

	public Integer getUseDays() {
		return useDays;
	}

	public void setUseDays(Integer useDays) {
		this.useDays = useDays;
	}

	public Boolean getIsChoice() {
		return isChoice;
	}

	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUserDateTypeName() {
		return userDateTypeName;
	}

	public void setUserDateTypeName(String userDateTypeName) {
		this.userDateTypeName = userDateTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public static ActCouponVo transfer(ActCoupon actCoupon) {
		ActCouponVo vo = new ActCouponVo();
		vo.copyProperity( actCoupon );
		
		vo.setTypeName(CouponType.fromId(actCoupon.getType()).getValue());
		
		CoPartner coPartner = actCoupon.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(actCoupon.getCoPartner().getName());
		}
		
		return vo;
	}
	
	public static List<ActCouponVo> transferList(List<ActCoupon> actCoupons) {
		List<ActCouponVo> vos = new ArrayList<>();
		
		for ( ActCoupon actCoupon : actCoupons ) {
			vos.add( transfer( actCoupon ) );
		}
		
		return vos;
	}
}
