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

import com.djy.consume.model.ConsumeOrder;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_sys_deposit_log")
@org.hibernate.annotations.Table(appliesTo="co_sys_deposit_log", comment="平台预存额日志")
public class CoSysDepositLog extends BaseModel {

	private CoPartner coPartner;
	
	private Integer type;
	private Integer incomeExpense;
	private Double amount;
	private Double sysDeposit;
	private Date insertTime;
	
	private ConsumeOrder consumeOrder;
	private CoSysDepositOrder coSysDepositOrder;
	
	
	
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
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '业务类型。1—商城订单提货，2—订货订单申请，3—订货订单驳回。'" )
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	@Column(
			name = "IncomeExpense", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '收支类型。-1：支出，0：收支，1：收入。'" )
	public Integer getIncomeExpense() {
		return incomeExpense;
	}
	public void setIncomeExpense(Integer incomeExpense) {
		this.incomeExpense = incomeExpense;
	}
	
	
	@Column(
			name = "Amount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '金额'" )
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	@Column(
			name = "SysDeposit", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '业务发生后剩余交货配额'" )
	public Double getSysDeposit() {
		return sysDeposit;
	}
	public void setSysDeposit(Double sysDeposit) {
		this.sysDeposit = sysDeposit;
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
			name = "consume_order_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户消费订单ID'" )
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_sys_deposit_order_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '平台预存订单ID'" )
	public CoSysDepositOrder getCoSysDepositOrder() {
		return coSysDepositOrder;
	}
	public void setCoSysDepositOrder(CoSysDepositOrder coSysDepositOrder) {
		this.coSysDepositOrder = coSysDepositOrder;
	}
	
	
	
	
}
