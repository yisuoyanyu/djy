package com.djy.consume.model;

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

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.model.CoSettleOrder;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.coupon.model.CouponDiscount;
import com.djy.fin.model.FinCharge;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;


@Entity
@Table(name = "consume_order")
@org.hibernate.annotations.Table(appliesTo="consume_order", comment="用户消费订单")
public class ConsumeOrder extends BaseModel {
	
	private String no;
	private User user;
	private CoPartner coPartner;
	private CoPartnerEmpl coPartnerEmpl;
	private Double consumeAmount;
	private Double payAmount;
	private Integer status;
	private Date insertTime;
	private FinCharge finCharge;
	
	private CouponDiscount couponDiscount;
	
	private CoSettleOrder coSettleOrder;
	
	
	
	@Column(
			name = "No", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '订单编号'" )
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
			name = "co_partner_empl_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '操作员（合作商家员工）ID'" )
	public CoPartnerEmpl getCoPartnerEmpl() {
		return coPartnerEmpl;
	}
	public void setCoPartnerEmpl(CoPartnerEmpl coPartnerEmpl) {
		this.coPartnerEmpl = coPartnerEmpl;
	}
	
	
	@Column(
			name = "ConsumeAmount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '消费金额'" )
	public Double getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	
	
	@Column(
			name = "PayAmount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '支付金额'" )
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未付款，2—付款中，3—付款成功，4—付款失败，5-已取消'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return ConsumeOrderStatus.fromId(status).getValue();
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "fin_charge_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '支付订单ID'" )
	public FinCharge getFinCharge() {
		return finCharge;
	}
	public void setFinCharge(FinCharge finCharge) {
		this.finCharge = finCharge;
	}
	
	
	@OneToOne(mappedBy = "consumeOrder", fetch = FetchType.LAZY)
	public CouponDiscount getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(CouponDiscount couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	
	@OneToOne(mappedBy = "consumeOrder", fetch = FetchType.LAZY)
	public CoSettleOrder getCoSettleOrder() {
		return coSettleOrder;
	}
	public void setCoSettleOrder(CoSettleOrder coSettleOrder) {
		this.coSettleOrder = coSettleOrder;
	}
	
	
}
