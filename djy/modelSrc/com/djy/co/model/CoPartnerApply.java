package com.djy.co.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.djy.user.model.User;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_partner_apply")
@org.hibernate.annotations.Table(appliesTo="co_partner_apply", comment="合作商家申请")
public class CoPartnerApply extends BaseModel{

	private User user;
	private String name;
	private String area;
	private String address;
	private String telephone;
	private String contact;
	private Integer status;
	private Date insertTime;
	
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
			name = "Name", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '加油站名称'" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(
			name = "Area", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '所在地区'" )
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(
			name = "Address", 
			length = 120, 
			columnDefinition = "VARCHAR(120) COMMENT '其他地址'" )
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Column(
			name = "Telephone", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '座机'" )
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	@Column(
			name = "Contact", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '联系人'" )
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。0--未处理，1—已处理'" )
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
