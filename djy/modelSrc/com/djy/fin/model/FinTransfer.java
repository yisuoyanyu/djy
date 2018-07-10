package com.djy.fin.model;

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

import com.djy.co.model.CoSettleOrder;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.enumtype.FinTransferType;
import com.djy.spread.model.SpreadCommission;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;



@Entity
@Table(name = "fin_transfer")
@org.hibernate.annotations.Table(appliesTo="fin_transfer", comment="公共的企业付款订单")
public class FinTransfer extends BaseModel {

	private Integer type;
	private User user;
	
	private Integer channel;
	private String orderNo;
	private Double amount;
	private String description;
	private Integer status;
	private Date insertTime;
	
	private CoSettleOrder coSettleOrder;
	private CoSysDepositOrder coSysDepositOrder;
	private SpreadCommission spreadCommission;
	
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(2) COMMENT '业务类型。1—商家结算，2—平台预存。'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return FinTransferType.fromId( type ).getName();
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
	
	
	@Column(
			name = "Channel", 
			nullable = false, 
			columnDefinition = "TINYINT(2) COMMENT '支付渠道。1—微信公众号支付。'" )
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	@Transient
	public String getChannelName() {
		return FinChannel.fromId( channel ).getValue();
	}
	
	
	@Column(
			name = "OrderNo", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。'" )
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	@Column(
			name = "Amount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '付款金额（元）'" )
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	@Column(
			name = "Description", 
			nullable = false, 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '订单附加说明'" )
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未支付，2—付款中，3—支付成功，4—支付失败。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
    		name = "co_settle_order_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '商家结算订单'" )
	public CoSettleOrder getCoSettleOrder() {
		return coSettleOrder;
	}
	public void setCoSettleOrder(CoSettleOrder coSettleOrder) {
		this.coSettleOrder = coSettleOrder;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "co_sys_deposit_order_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '平台预存订单'" )
	public CoSysDepositOrder getCoSysDepositOrder() {
		return coSysDepositOrder;
	}
	public void setCoSysDepositOrder(CoSysDepositOrder coSysDepositOrder) {
		this.coSysDepositOrder = coSysDepositOrder;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "spread_commission_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '用户推广佣金'" )
	public SpreadCommission getSpreadCommission() {
		return spreadCommission;
	}
	public void setSpreadCommission(SpreadCommission spreadCommission) {
		this.spreadCommission = spreadCommission;
	}
	
	
	
}
