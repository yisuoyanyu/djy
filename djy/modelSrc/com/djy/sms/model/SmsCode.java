package com.djy.sms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.frame.base.model.BaseModel;



@Entity
@Table(name = "sms_code")
@org.hibernate.annotations.Table(appliesTo = "sms_code", comment = "短信验证码")
public class SmsCode extends BaseModel {
	
	private Integer scene;
	private String mobile;
	private String clientIP;
	private String token;
	private String code;
	private Date insertTime = new Date();
	
	
	@Column(
			name = "Scene", 
			nullable = false, 
			columnDefinition = "INT(11) COMMENT '场景。1001—用户注册，1002—绑定手机号。'" )
	public Integer getScene() {
		return scene;
	}
	public void setScene(Integer scene) {
		this.scene = scene;
	}
	
	
	@Column(
			name = "Mobile", 
			nullable = false, 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '手机'" )
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	@Column(
			name = "ClientIP",
			length = 15, 
			columnDefinition = "VARCHAR(15) COMMENT '发起验证客户端的 IP 地址，格式为 IPv4 整型，如 127.0.0.1。'" )
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	
	@Column(
			name = "Token", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '传给客户端的token，验证短信验证码时一起验证。'" )
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	@Column(
			name = "Code", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '短信验证码'" )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
