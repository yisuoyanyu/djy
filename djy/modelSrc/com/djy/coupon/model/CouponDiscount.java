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

import com.djy.co.model.CoPartner;
import com.djy.consume.model.ConsumeOrder;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;


@Entity
@Table(name = "coupon_discount")
@org.hibernate.annotations.Table(appliesTo="coupon_discount", comment="打折券")
public class CouponDiscount extends BaseModel {
	
	private User user;
	private String title;
	private String no;
	private ActCoupon actCoupon;
	private CoPartner coPartner;
	private Double discountPercent;
	private Double useMinConsume;
	private Date useStartDate;
	private Date useEndDate;
	private Integer status;
	private Date usedTime;
	private ConsumeOrder consumeOrder;
	private Date insertTime;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "user_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '合作商家ID'" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
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
	
	
	@Column(
			name = "No", 
			nullable = false,
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '券号'" )
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_partner_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '合作商家ID'" )
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "act_coupon_ID", 
			nullable = true, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '送券活动ID'" )
	public ActCoupon getActCoupon() {
		return actCoupon;
	}
	public void setActCoupon(ActCoupon actCoupon) {
		this.actCoupon = actCoupon;
	}
	
	
	@Column(
			name = "DiscountPercent", 
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
	
	
	@Temporal(TemporalType.DATE)
	@Column(
			name = "UseStartDate",
			columnDefinition = "DATE COMMENT '“按区间”下，打折券可使用开始时间'" )
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
	public Date getUseEndDate() {
		return useEndDate;
	}
	public void setUseEndDate(Date useEndDate) {
		this.useEndDate = useEndDate;
	}
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未使用，2—已占用，3—已使用。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return CouponDiscountStatus.fromId(status).getValue();
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "UsedTime", 
			columnDefinition = "DATETIME COMMENT '在什么时候被使用时间'" )
	public Date getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	
	
	@ManyToOne
	@JoinColumn(
			name = "consume_order_ID", 
			columnDefinition = "INT(11) COMMENT '被使用在哪个消费订单上'" )
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
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
}
