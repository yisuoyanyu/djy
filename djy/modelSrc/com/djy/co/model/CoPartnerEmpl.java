package com.djy.co.model;

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

import com.djy.co.enumtype.CoPartnerEmplStatus;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_partner_empl")
@org.hibernate.annotations.Table(appliesTo = "co_partner_empl", comment = "合作商家员工")
public class CoPartnerEmpl extends BaseModel {
	
	private CoPartner coPartner;
	private String emplID;
	private String realName;
	private String idcard;
	private String mobile;
	private String email;
	private Integer status;
	private User user;
	private Date insertTime;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_partner_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '所属商家ID'" )
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	
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
		return CoPartnerEmplStatus.fromId( status ).getValue();
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
