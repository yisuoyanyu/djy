package com.djy.wxPay.model;

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

import com.djy.fin.model.FinCharge;
import com.frame.base.model.BaseModel;


@Entity
@Table(name = "wx_pay_charge")
@org.hibernate.annotations.Table(appliesTo="wx_pay_charge", comment="微信支付 支付订单")
public class WxPayCharge extends BaseModel {
	
	private FinCharge finCharge;
	
	private String appid;
	private String mchId;
	private String deviceInfo;
	private String body;
	private String detail;
	private String attach;
	private String outTradeNo;
	private String feeType;
	private Integer totalFee;
	private String spbillCreateIp;
	private String timeStart;
	private String timeExpire;
	private String goodsTag;
	private String tradeType;
	private String productId;
	private String limitPay;
	private String openid;
	private String sceneInfo;
	private String resultCode;
	private String errCode;
	private String errCodeDes;
	private String prepayId;
	private String codeUrl;
	private Integer status;
	private Date insertTime;
	
	private String timeStamp;
	private String nonceStr;
	private String packageStr;
	private String signType;
	private String paySign;
	
	private WxPayNotice wxPayNotice;
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "fin_charge_ID", 
    		nullable = false, 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '公共的支付订单ID'" )
	public FinCharge getFinCharge() {
		return finCharge;
	}
	public void setFinCharge(FinCharge finCharge) {
		this.finCharge = finCharge;
	}
	
	
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
			name = "Body", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '商品描述'" )
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	@Column(
			name = "Detail", 
			length = 6000, 
			columnDefinition = "VARCHAR(6000) COMMENT '商品详情'" )
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	@Column(
			name = "Attach", 
			length = 127, 
			columnDefinition = "VARCHAR(127) COMMENT '附加数据'" )
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	
	@Column(
			name = "OutTradeNo", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商户系统内部订单号'" )
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	
	@Column(
			name = "FeeType", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '标价币种（默认人民币：CNY）'" )
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
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
			name = "SpbillCreateIp", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT 'APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。'" )
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	
	
	@Column(
			name = "TimeStart", 
			length = 14, 
			columnDefinition = "VARCHAR(14) COMMENT '交易起始时间'" )
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	
	
	@Column(
			name = "TimeExpire", 
			length = 14, 
			columnDefinition = "VARCHAR(14) COMMENT '交易结束时间'" )
	public String getTimeExpire() {
		return timeExpire;
	}
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}
	
	
	@Column(
			name = "GoodsTag", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '订单优惠标记，使用代金券或立减优惠功能时需要的参数'" )
	public String getGoodsTag() {
		return goodsTag;
	}
	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}
	
	
	@Column(
			name = "TradeType", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '交易类型，取值为：JSAPI，NATIVE，APP等'" )
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	
	@Column(
			name = "ProductId", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '商品ID。交易类型为NATIVE（即扫码支付）时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。'" )
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	@Column(
			name = "LimitPay", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '指定支付方式'" )
	public String getLimitPay() {
		return limitPay;
	}
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	
	
	@Column(
			name = "Openid", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '用户标识。交易类型为JSAPI（即公众号支付）时，此参数必传，此参数为微信用户在商户对应appid下的唯一标识。'" )
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	@Column(
			name = "SceneInfo", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '场景信息'" )
	public String getSceneInfo() {
		return sceneInfo;
	}
	public void setSceneInfo(String sceneInfo) {
		this.sceneInfo = sceneInfo;
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
			name = "PrepayId", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时'" )
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	
	
	@Column(
			name = "CodeUrl", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '交易类型为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付'" )
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
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
	
	
	@Column(
			name = "TimeStamp", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '时间戳'" )
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
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
			name = "PackageStr", 
			length = 128, 
			columnDefinition = "VARCHAR(128) COMMENT '订单详情扩展字符串'" )
	public String getPackageStr() {
		return packageStr;
	}
	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}
	
	
	@Column(
			name = "SignType", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '签名方式。签名算法，暂支持MD5'" )
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	
	@Column(
			name = "PaySign", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '签名'" )
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
    		name = "wx_pay_notice_ID", 
    		columnDefinition = "INT(11) UNSIGNED COMMENT '微信支付监听ID'" )
	public WxPayNotice getWxPayNotice() {
		return wxPayNotice;
	}
	public void setWxPayNotice(WxPayNotice wxPayNotice) {
		this.wxPayNotice = wxPayNotice;
	}
	
	
}
