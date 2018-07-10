package com.djy.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.djy.consume.model.ConsumeOrder;
import com.djy.coupon.model.CouponDiscount;
import com.djy.user.enumtype.MessageType;
import com.frame.base.model.BaseModel;


@Entity
@Table(name = "user_site_msg")
@org.hibernate.annotations.Table(appliesTo="user_site_msg", comment="用户站内消息")
public class UserSiteMsg extends BaseModel {

	private User user;
	
	private Integer type;
	private String content;
	private Date insertTime = new Date();
	private Date readTime;
	private ConsumeOrder consumeOrder;
	private CouponDiscount couponDiscount;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "user_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户ID'" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '消息类型。1—系统通知，2—订单消息，3—优惠券。'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return MessageType.fromId(type).getValue();
	}
	
	
	@Column(
			name = "Content", 
			nullable = false,
			length = 255,
			columnDefinition = "VARCHAR(255) COMMENT '内容'" )
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
			name = "ReadTime",
			columnDefinition = "DATETIME COMMENT '阅读时间（消息打开时间）'" )
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	@ManyToOne          
    @JoinColumn(
    		name = "consume_order_ID",
    		columnDefinition = "INT(11) COMMENT '用户消费ID'" )
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
	}
	
	@ManyToOne          
    @JoinColumn(
    		name = "coupon_discount_ID",
    		columnDefinition = "INT(11) COMMENT '用户打折券ID'" )
	public CouponDiscount getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(CouponDiscount couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
}
