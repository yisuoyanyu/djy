package com.djy.spread.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.djy.spread.enumtype.SpreadPromoterStatus;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "spread_promoter")
@org.hibernate.annotations.Table(appliesTo = "spread_promoter", comment = "推广人")
public class SpreadPromoter extends BaseModel {
	
	private String emplID;
	private String realName;
	private String idcard;
	private String mobile;
	private String email;
	private Integer status;
	private User user;
	private Double spreadConsumeFirstComm;
	private Date insertTime;
	
	@Column(
			name = "EmplID", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '工号'" )
	public String getEmplID() {
		return emplID;
	}
	public void setEmplID(String emplID) {
		this.emplID = emplID;
	}
	
	@Column(
			name = "RealName", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '姓名'" )
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(
			name = "Idcard", 
			length = 18, 
			columnDefinition = "VARCHAR(18) COMMENT '身份证'" )
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Column(
			name = "Mobile", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '绑定手机'" )
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(
			name = "Email", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '邮箱'" )
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(
			name = "Status", 
			nullable = false,
			columnDefinition = "TINYINT(1) COMMENT '1—正常，2—冻结。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return SpreadPromoterStatus.fromId( status ).getValue();
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "user_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户ID'" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(
			name = "SpreadConsumeFirstComm", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '推广首次消费佣金'" )
	public Double getSpreadConsumeFirstComm() {
		return spreadConsumeFirstComm;
	}
	public void setSpreadConsumeFirstComm(Double spreadConsumeFirstComm) {
		this.spreadConsumeFirstComm = spreadConsumeFirstComm;
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
