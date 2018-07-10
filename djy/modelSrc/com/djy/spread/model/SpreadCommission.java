package com.djy.spread.model;

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

import com.djy.consume.model.ConsumeOrder;
import com.djy.fin.model.FinTransfer;
import com.djy.spread.enumtype.SpreadCommissionStatus;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "spread_commission")
@org.hibernate.annotations.Table(appliesTo = "spread_commission", comment = "推广佣金")
public class SpreadCommission extends BaseModel {
	
	private String no;
	private Double amount;
	private Integer status;
	private Date insertTime;
	private SpreadPromoter spreadPromoter;
	private ConsumeOrder consumeOrder;
	private FinTransfer finTransfer;
	
	@Column(
			name = "No", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '订单编号'" )
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
		return SpreadCommissionStatus.fromId( status ).getValue();
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "spread_promoter_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '所属推广人ID'" )
	public SpreadPromoter getSpreadPromoter() {
		return spreadPromoter;
	}
	public void setSpreadPromoter(SpreadPromoter spreadPromoter) {
		this.spreadPromoter = spreadPromoter;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "consume_order_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '消费订单ID'" )
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
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
