package com.djy.coupon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.djy.co.model.CoPartner;
import com.djy.coupon.enumtype.ActCouponStatus;
import com.djy.coupon.enumtype.CouponType;
import com.djy.coupon.enumtype.CouponUseDateType;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "act_coupon")
@org.hibernate.annotations.Table(appliesTo="act_coupon", comment="送券活动")
public class ActCoupon extends BaseModel {
	
	private String title;
	private CoPartner coPartner;
	private Date startTime;
	private Date endTime;
	private Integer type;
	private Double discountPercent;
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
	
	@Column(
			name = "Title", 
			nullable = false,
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_partner_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '合作商家ID'" )
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "StartTime", 
			columnDefinition = "DATETIME COMMENT '开始时间。如值为NULL，则不限制'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "EndTime", 
			columnDefinition = "DATETIME COMMENT '结束时间。如值为NULL，则不限制'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '优惠券类型。1—打折券'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return CouponType.fromId( type ).getValue();
	}
	
	
	@Column(
			name = "DiscountPercent", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '折扣百分比'" )
	public Double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	
	@Column(
			name = "UseMinConsume", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '最低消费金额（满赠）'" )
	public Double getUseMinConsume() {
		return useMinConsume;
	}
	public void setUseMinConsume(Double useMinConsume) {
		this.useMinConsume = useMinConsume;
	}
	
	@Column(
			name = "UseDateType", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '打折券可使用日期类型。1—不限制（anyDate），2—按区间（period），3—按天数（days）'" )
	public Integer getUseDateType() {
		return useDateType;
	}
	public void setUseDateType(Integer useDateType) {
		this.useDateType = useDateType;
	}
	@Transient
	public String getUseDateTypeName() {
		if (useDateType != null) {
			return CouponUseDateType.fromId( useDateType ).getValue();
		} else {
			return null;
		}
	}
	
	@Temporal(TemporalType.DATE)
	@Column(
			name = "UseStartDate",
			columnDefinition = "DATE COMMENT '“按区间”下，打折券可使用开始时间'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getUseStartDate() {
		return useStartDate;
	}
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(
			name = "UseEndDate",
			columnDefinition = "DATE COMMENT '“按区间”下，打折券可使用结束时间'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getUseEndDate() {
		return UseEndDate;
	}
	public void setUseEndDate(Date useEndDate) {
		UseEndDate = useEndDate;
	}
	
	@Column(
			name = "UseDays", 
			columnDefinition = "INT(1) COMMENT '“按天数”下，几天内'" )
	public Integer getUseDays() {
		return useDays;
	}
	public void setUseDays(Integer useDays) {
		this.useDays = useDays;
	}
	
	@Column(
			name = "IsChoice", 
			columnDefinition = "TINYINT(1) COMMENT '是否精选'" )
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}
	
	@Column(
			name = "Status", 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—启用，0—禁用'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return ActCouponStatus.fromId(status).getValue();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "InsertTime", 
			nullable = false, 
			columnDefinition = "DATETIME COMMENT '插入时间'" )
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "LastTime", 
			columnDefinition = "DATETIME COMMENT '最后修改时间'" )
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	@Column(
			name = "RewardNum", 
			columnDefinition = "INT(11) COMMENT '已赠送次数'" )
	public Integer getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(Integer rewardNum) {
		this.rewardNum = rewardNum;
	}
	
	
}
