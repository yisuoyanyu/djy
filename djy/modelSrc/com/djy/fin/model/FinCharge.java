package com.djy.fin.model;

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
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.enumtype.FinChargeType;
import com.djy.user.model.User;
import com.djy.wxPay.model.WxPayCharge;
import com.frame.base.model.BaseModel;



@Entity
@Table(name = "fin_charge")
@org.hibernate.annotations.Table(appliesTo="fin_charge", comment="公共的支付订单")
public class FinCharge extends BaseModel {
	
	private Integer type;
	private User user;
	
	private Integer channel;
	private String orderNo;
	private String clientIp;
	private String subject;
	private String body;
	private Double amount;
	private Boolean paid;
	private Date timePaid;
	private String failureCode;
	private String failureMsg;
	private Integer status;
	private Date insertTime;
	
	private ConsumeOrder consumeOrder;
	
	private WxPayCharge wxPayCharge;
	
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(2) COMMENT '业务类型。1—充值订单，2—用户消费订单。'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return FinChargeType.fromId( type ).getName();
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
			name = "ClientIp", 
			nullable = false, 
			length = 15, 
			columnDefinition = "VARCHAR(15) COMMENT '发起支付请求客户端的 IP 地址，格式为 IPv4 整型，如 127.0.0.1。'" )
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	
	@Column(
			name = "Subject", 
			nullable = false, 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '商品标题'" )
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	@Column(
			name = "Body", 
			length = 6000, 
			columnDefinition = "VARCHAR(6000) COMMENT '商品描述信息'" )
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	@Column(
			name = "Amount", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '订单金额（元）'" )
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	@Column(
			name = "Paid", 
			columnDefinition = "TINYINT(1) COMMENT '是否付款。1—已付款。'" )
	public Boolean getPaid() {
		return paid;
	}
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "TimePaid", 
    		columnDefinition = "DATETIME COMMENT '订单完成时间'" )
	public Date getTimePaid() {
		return timePaid;
	}
	public void setTimePaid(Date timePaid) {
		this.timePaid = timePaid;
	}
	
	
	@Column(
			name = "FailureCode", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '订单的错误码'" )
	public String getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}
	
	
	@Column(
			name = "FailureMsg", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '订单的错误信息描述'" )
	public String getFailureMsg() {
		return failureMsg;
	}
	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
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
    		name = "consume_order_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '用户消费订单'" )
	public ConsumeOrder getConsumeOrder() {
		return consumeOrder;
	}
	public void setConsumeOrder(ConsumeOrder consumeOrder) {
		this.consumeOrder = consumeOrder;
	}
	
	
	@OneToOne(mappedBy = "finCharge", fetch = FetchType.LAZY)
	public WxPayCharge getWxPayCharge() {
		return wxPayCharge;
	}
	public void setWxPayCharge(WxPayCharge wxPayCharge) {
		this.wxPayCharge = wxPayCharge;
	}
	
	
}
