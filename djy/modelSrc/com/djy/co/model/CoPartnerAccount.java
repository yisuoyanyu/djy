package com.djy.co.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_partner_account")
@org.hibernate.annotations.Table(appliesTo="co_partner_account", comment="合作商家账户")
public class CoPartnerAccount extends BaseModel {

	private CoPartner coPartner;
	private Double sysDeposit = 0d;
	private Double totalSysDeposit = 0d;
	private Double totalCstmConsume = 0d;
	private Double totalCstmPay = 0d;
	private Double totalCstmSettle = 0d;
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
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
	
	
	@Column(
			name = "SysDeposit", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '“平台预存”模式下，平台预存金额。'" )
	public Double getSysDeposit() {
		return sysDeposit;
	}
	public void setSysDeposit(Double sysDeposit) {
		this.sysDeposit = sysDeposit;
	}
	
	
	@Column(
			name = "TotalSysDeposit", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '“平台预存”模式下，平台充值总额。'" )
	public Double getTotalSysDeposit() {
		return totalSysDeposit;
	}
	public void setTotalSysDeposit(Double totalSysDeposit) {
		this.totalSysDeposit = totalSysDeposit;
	}
	
	
	@Column(
			name = "TotalCstmConsume", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '会员消费总额'" )
	public Double getTotalCstmConsume() {
		return totalCstmConsume;
	}
	public void setTotalCstmConsume(Double totalCstmConsume) {
		this.totalCstmConsume = totalCstmConsume;
	}
	
	
	@Column(
			name = "TotalCstmPay", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '会员支付总额'" )
	public Double getTotalCstmPay() {
		return totalCstmPay;
	}
	public void setTotalCstmPay(Double totalCstmPay) {
		this.totalCstmPay = totalCstmPay;
	}
	
	
	@Column(
			name = "TotalCstmSettle", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '“每单结算”模式下，会员消费平台结算总额。'" )
	public Double getTotalCstmSettle() {
		return totalCstmSettle;
	}
	public void setTotalCstmSettle(Double totalCstmSettle) {
		this.totalCstmSettle = totalCstmSettle;
	}
	
	
}
