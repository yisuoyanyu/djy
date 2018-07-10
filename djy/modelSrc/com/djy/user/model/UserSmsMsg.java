package com.djy.user.model;

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
@Table(name = "user_sms_msg")
@org.hibernate.annotations.Table(appliesTo="user_sms_msg", comment="用户短信消息")
public class UserSmsMsg extends BaseModel {

	private User user;
	
	private String mobile;
	private String content;
	private Date insertTime = new Date();
	private Date sendTime;
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "user_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户ID'" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Column(
			name = "Mobile", 
			nullable = false,
			length = 16,
			columnDefinition = "VARCHAR(16) COMMENT '接收手机号'" )
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	@Column(
			name = "Content", 
			nullable = false,
			length = 255,
			columnDefinition = "VARCHAR(255) COMMENT '内容'" )
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(
			name = "SendTime",
			columnDefinition = "DATETIME COMMENT '实际发送时间'" )
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
