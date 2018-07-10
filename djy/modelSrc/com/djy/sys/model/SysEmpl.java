package com.djy.sys.model;

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

import com.djy.sys.enumtype.SysEmplStatus;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "sys_empl")
@org.hibernate.annotations.Table(appliesTo = "sys_empl", comment = "职员")
public class SysEmpl extends BaseModel {
	
	private String emplID;
	private String password;
	private String realName;
	private String idcard;
	private String mobile;
	private String email;
	private Integer status;
	private User user;
	private Double indexPercent;
	private Double bonusPercent;
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
			name = "Password", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '工号'" )
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
		return SysEmplStatus.fromId( status ).getValue();
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
			name = "IndexPercent", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '指标比例，营业额占充值额的百分比。'" )
	public Double getIndexPercent() {
		return indexPercent;
	}
	public void setIndexPercent(Double indexPercent) {
		this.indexPercent = indexPercent;
	}
	
	@Column(
			name = "BonusPercent", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '提成比例，占营业额的百分比。'" )
	public Double getBonusPercent() {
		return bonusPercent;
	}
	public void setBonusPercent(Double bonusPercent) {
		this.bonusPercent = bonusPercent;
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
