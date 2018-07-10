package com.djy.wxPay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.djy.fin.model.FinTransfer;
import com.frame.base.model.BaseModel;



@Entity
@Table(name = "wx_pay_transfer")
@org.hibernate.annotations.Table(appliesTo="wx_pay_transfer", comment="微信支付 支付订单")
public class WxPayTransfer extends BaseModel {
	
	private FinTransfer finTransfer;
	
	private String mchAppid;
	private String mchid;
	private String deviceInfo;
	private String nonceStr;
	private String partnerTradeNo;
	private String openid;
	private String checkName;
	private String reUserName;
	private Integer amount;
	private String description;
	private String spbillCreateIp;
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	private String paymentNo;
	private String paymentTime;
	
	private Integer status;
	private Date insertTime;
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "fin_transfer_ID", 
    		nullable = false, 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '公共的企业付款订单'" )
	public FinTransfer getFinTransfer() {
		return finTransfer;
	}
	public void setFinTransfer(FinTransfer finTransfer) {
		this.finTransfer = finTransfer;
	}
	
	
	@Column(
			name = "MchAppid", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商户账号appid。微信分配的账号ID（企业号corpid即为此appId）'" )
	public String getMchAppid() {
		return mchAppid;
	}
	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}
	
	
	@Column(
			name = "Mchid", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '微信支付分配的商户号'" )
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	
	
	@Column(
			name = "DeviceInfo", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '设备号'" )
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	
	@Column(
			name = "NonceStr", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '随机字符串'" )
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	
	@Column(
			name = "PartnerTradeNo", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商户订单号'" )
	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}
	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}
	
	
	@Column(
			name = "Openid", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '用户openid。商户appid下，某用户的openid。'" )
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	@Column(
			name = "CheckName", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '校验用户姓名选项。NO_CHECK：不校验真实姓名，FORCE_CHECK：强校验真实姓名。'" )
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	
	@Column(
			name = "ReUserName", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '收款用户姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名。'" )
	public String getReUserName() {
		return reUserName;
	}
	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}
	
	
	@Column(
			name = "Amount", 
			columnDefinition = "INT(11) COMMENT '企业付款金额，单位为分。'" )
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	@Column(
			name = "Description", 
			length = 128, 
			columnDefinition = "企业付款操作说明信息。必填。'" )
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(
			name = "SpbillCreateIp", 
			length = 16, 
			columnDefinition = "调用接口的机器Ip地址'" )
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	
	
	@Column(
			name = "ResultCode", 
			length = 16, 
			columnDefinition = "业务结果：SUCCESS/FAIL'" )
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	
	@Column(
			name = "ErrCode", 
			length = 32, 
			columnDefinition = "错误码信息'" )
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	
	@Column(
			name = "ErrCodeDes", 
			length = 128, 
			columnDefinition = "错误代码描述'" )
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	
	
	@Column(
			name = "PaymentNo", 
			length = 32, 
			columnDefinition = "企业付款成功，返回的微信订单号'" )
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	
	@Column(
			name = "PaymentTime", 
			length = 14, 
			columnDefinition = "微信支付成功时间'" )
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—未支付，2—付款中，3—支付成功，3—支付失败。'" )
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
	
	
}
