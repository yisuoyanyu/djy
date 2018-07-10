package com.djy.wxPay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.frame.base.model.BaseModel;


@Entity
@Table(name = "wx_pay_notice")
@org.hibernate.annotations.Table(appliesTo="wx_pay_notice", comment="微信支付 监听消息")
public class WxPayNotice extends BaseModel {
	
	private String appid;
	private String mchId;
	private String deviceInfo;
	private String nonceStr;
	
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	
	private String openid;
	private String isSubscribe;
	
	private String tradeType;
	private String bankType;
	
	private Integer totalFee;
	private Integer settlementTotalFee;
	private String feeType;
	
	private Integer cashFee;
	private String cashFeeType;
	
	private Integer couponFee;
	private Integer couponCount;
	
	private String transactionId;
	private String outTradeNo;
	
	private String attach;
	
	private String timeEnd;
	
	private String resultXml;
	
	private Date insertTime;
	
	
	
	@Column(
			name = "Appid", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '公众账号ID'" )
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	
	@Column(
			name = "MchId", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商户号'" )
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
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
			name = "ResultCode", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '业务结果：SUCCESS/FAIL'" )
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	
	@Column(
			name = "ErrCode", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '错误代码'" )
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	
	@Column(
			name = "ErrCodeDes", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '错误代码描述'" )
	public String getErrCodeDes() {
		return errCodeDes;
	}
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}
	
	
	@Column(
			name = "Openid", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '用户标识'" )
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	@Column(
			name = "IsSubscribe", 
			length = 1, 
			columnDefinition = "VARCHAR(1) COMMENT '用户是否关注公众账号，Y-关注，N-未关注。'" )
	public String getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	
	
	@Column(
			name = "TradeType", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '交易类型：JSAPI、NATIVE、APP'" )
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	
	@Column(
			name = "BankType", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '付款银行'" )
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	
	@Column(
			name = "TotalFee", 
			columnDefinition = "INT(11) COMMENT '订单总金额，单位为分。'" )
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	
	
	@Column(
			name = "SettlementTotalFee", 
			columnDefinition = "INT(11) COMMENT '应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。'" )
	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}
	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}
	
	
	@Column(
			name = "FeeType", 
			length = 8, 
			columnDefinition = "VARCHAR(8) COMMENT '货币种类'" )
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	
	@Column(
			name = "CashFee", 
			columnDefinition = "INT(11) COMMENT '现金支付金额'" )
	public Integer getCashFee() {
		return cashFee;
	}
	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}
	
	
	@Column(
			name = "CashFeeType", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '现金支付货币类型'" )
	public String getCashFeeType() {
		return cashFeeType;
	}
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}
	
	
	@Column(
			name = "CouponFee", 
			columnDefinition = "INT(11) COMMENT '总代金券金额'" )
	public Integer getCouponFee() {
		return couponFee;
	}
	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}
	
	
	@Column(
			name = "CouponCount", 
			columnDefinition = "INT(11) COMMENT '代金券使用数量'" )
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
	
	@Column(
			name = "TransactionId", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '微信支付订单号'" )
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
	@Column(
			name = "OutTradeNo", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商户订单号'" )
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	
	@Column(
			name = "Attach", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '商家数据包'" )
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	
	@Column(
			name = "TimeEnd", 
			length = 14, 
			columnDefinition = "VARCHAR(14) COMMENT '支付完成时间'" )
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	
	@Column(
			name = "ResultXml", 
			columnDefinition = "TEXT COMMENT '返回的数据'" )
	public String getResultXml() {
		return resultXml;
	}
	public void setResultXml(String resultXml) {
		this.resultXml = resultXml;
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
