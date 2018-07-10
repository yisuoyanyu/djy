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
import javax.persistence.Transient;

import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.model.FinTransfer;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_settle_order")
@org.hibernate.annotations.Table(appliesTo="co_settle_order", comment="商家结算订单")
public class CoSettleOrder extends BaseModel {
	
	private String no;
	private CoPartner coPartner;
	private Double amount;
	private ConsumeOrder consumeOrder;
	private Integer status;
	private Date insertTime;
	private FinTransfer finTransfer;
	
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
			name = "Amount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '结算金额'" )
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "consume_order_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '“每单结算”模式下，相关的用户消费订单。'" )	
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
	}
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未付款，2—付款中，3—付款完成，4—付款失败'" )
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
			name = "fin_transfer_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '支付订单ID'" )
	public FinTransfer getFinTransfer() {
		return finTransfer;
	}
	public void setFinTransfer(FinTransfer finTransfer) {
		this.finTransfer = finTransfer;
	}
	
}
