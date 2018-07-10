package com.djy.co.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.frame.base.model.BaseModel;


@Entity
@Table(name = "co_partner_shift")
@org.hibernate.annotations.Table(appliesTo="co_partner_shift", comment="商家交接班")
public class CoPartnerShift extends BaseModel {
	
	private CoPartner coPartner;
	
	private Double totalCstmConsume;
	private Double totalCstmPay;
	private Double totalCstmSettle;
	private Double totalSysDepositPay;
	private Double totalSysDeposit;
	private Double startSysDeposit;
	private Double endSysDeposit;
	private Date startTime;
	private Date endTime;
	
	
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
	
	
	@Column(
			name = "TotalCstmConsume", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '会员消费总额'" )
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
			columnDefinition = "DOUBLE(12,2) COMMENT '会员实付总额'" )
	public Double getTotalCstmPay() {
		return totalCstmPay;
	}
	public void setTotalCstmPay(Double totalCstmPay) {
		this.totalCstmPay = totalCstmPay;
	}
	
	@Column(
			name = "TotalCstmSettle", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '“每单结算”模式下，会员消费平台结算总额'" )
	public Double getTotalCstmSettle() {
		return totalCstmSettle;
	}
	public void setTotalCstmSettle(Double totalCstmSettle) {
		this.totalCstmSettle = totalCstmSettle;
	}
	
	@Column(
			name = "TotalSysDepositPay", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '“平台预存”模式下，平台预存支付总额" )
	public Double getTotalSysDepositPay() {
		return totalSysDepositPay;
	}
	public void setTotalSysDepositPay(Double totalSysDepositPay) {
		this.totalSysDepositPay = totalSysDepositPay;
	}
	
	@Column(
			name = "TotalSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '“平台预存”模式下，平台预存总额" )
	public Double getTotalSysDeposit() {
		return totalSysDeposit;
	}
	public void setTotalSysDeposit(Double totalSysDeposit) {
		this.totalSysDeposit = totalSysDeposit;
	}
	
	@Column(
			name = "StartSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '“平台预存”模式下，平台预存总额" )
	public Double getStartSysDeposit() {
		return startSysDeposit;
	}
	public void setStartSysDeposit(Double startSysDeposit) {
		this.startSysDeposit = startSysDeposit;
	}
	
	@Column(
			name = "EndSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '“平台预存”模式下，平台预存总额" )
	public Double getEndSysDeposit() {
		return endSysDeposit;
	}
	public void setEndSysDeposit(Double endSysDeposit) {
		this.endSysDeposit = endSysDeposit;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "StartTime", 
    		columnDefinition = "DATETIME COMMENT '交接开始时间'" )
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "EndTime", 
    		nullable = false,
    		columnDefinition = "DATETIME COMMENT '交接开始时间'" )
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
