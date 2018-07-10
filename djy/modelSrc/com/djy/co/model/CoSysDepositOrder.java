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

import com.djy.co.enumtype.CoSysDepositOrderStatus;
import com.djy.fin.model.FinTransfer;
import com.djy.sys.model.SysUser;
import com.frame.base.model.BaseModel;



@Entity
@Table(name = "co_sys_deposit_order")
@org.hibernate.annotations.Table(appliesTo="co_sys_deposit_order", comment="平台预存订单")
public class CoSysDepositOrder extends BaseModel {
	
	private String no;
	private CoPartner coPartner;
	private Double amount;
	private CoSysDepositLog coSysDepositLog;
	private Double payAmount;
	private Integer status;
	private Date insertTime;
	private FinTransfer finTransfer;
	private SysUser sysUser;
	
	
	
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
			columnDefinition = "DOUBLE(12,2) COMMENT '预存金额'" )
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_sys_deposit_log_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '“预存金额”模式下，触发系统自动预存的平台预存金额日志。如平台手动录入的预存，则该字段为空'" )
	public CoSysDepositLog getCoSysDepositLog() {
		return coSysDepositLog;
	}
	public void setCoSysDepositLog(CoSysDepositLog coSysDepositLog) {
		this.coSysDepositLog = coSysDepositLog;
	}
	
	
	@Column(
			name = "PayAmount", 
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
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未付款，2—付款中，3—付款完成，4—付款失败'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return CoSysDepositOrderStatus.fromId(status).getValue();
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "sys_user_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '工作人员ID'" )
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
	
}
