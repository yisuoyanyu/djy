package com.djy.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.frame.base.model.BaseModel;



@Entity
@Table(name = "user_account")
@org.hibernate.annotations.Table(appliesTo="user_account", comment="用户账户")
public class UserAccount extends BaseModel {

	private User user;

	private Double totalConcume;
	private Double totalPayConcume;
	private Integer canReceiveCoupons;
	
	
	
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
			name = "TotalConcume", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '累计消费金额'" )
	public Double getTotalConcume() {
		return totalConcume;
	}
	public void setTotalConcume(Double totalConcume) {
		this.totalConcume = totalConcume;
	}
	
	
	@Column(
			name = "TotalPayConcume", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '累计消费支付金额'" )
	public Double getTotalPayConcume() {
		return totalPayConcume;
	}
	public void setTotalPayConcume(Double totalPayConcume) {
		this.totalPayConcume = totalPayConcume;
	}
	
	
	@Transient
	public Double getTotalEconomise() {
		return totalConcume - totalPayConcume;
	}
	
	
	@Column(
			name = "CanReceiveCoupons", 
			nullable = false, 
			columnDefinition = "TINYINT(11) COMMENT '剩几张优惠券可领取'" )
	public Integer getCanReceiveCoupons() {
		return canReceiveCoupons;
	}
	public void setCanReceiveCoupons(Integer canReceiveCoupons) {
		this.canReceiveCoupons = canReceiveCoupons;
	}
	
	
}
