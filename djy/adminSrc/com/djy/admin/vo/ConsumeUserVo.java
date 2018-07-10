package com.djy.admin.vo;

import java.util.Date;

import com.frame.base.web.vo.BaseVo;


public class ConsumeUserVo extends BaseVo{
	private Integer uesrId;
	private String username;
	private String userMobile;
	private String consumeOrderNo;
	private Double consumeAmount;
	private Double payAmount;
	private Integer status;
	private Date insertTime;
	
	public Integer getUesrId() {
		return uesrId;
	}

	public void setUesrId(Integer uesrId) {
		this.uesrId = uesrId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getConsumeOrderNo() {
		return consumeOrderNo;
	}

	public void setConsumeOrderNo(String consumeOrderNo) {
		this.consumeOrderNo = consumeOrderNo;
	}

	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}
