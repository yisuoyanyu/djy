package com.djy.sys.model;

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

import com.djy.sys.enumtype.SysUserStatus;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Table(appliesTo = "sys_user", comment = "平台工作人员")
public class SysUser extends BaseModel {
	
	private String username;
	private String password;
	private String realName;
	private String mobile;
	private SysRole sysRole;
	private Integer status;
	private Date insertTime;
	
	
	@Column(
			name = "Username", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '用户名'" )
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Column(
			name = "Password", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '密码'" )
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Column(
			name = "RealName", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '真实姓名'" )
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "sys_role_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户角色编号'" )
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
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
		return SysUserStatus.fromId( status ).getValue();
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
