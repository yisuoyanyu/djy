package com.djy.admin.vo;

import java.util.Date;

import com.frame.base.web.vo.BaseVo;

public class ConsumeOrderExcelVo extends BaseVo {
	
	private String no;
	private String nickname;
	private String mobile;
	private Double consumeAmount;
	private Double payAmount;
	private String statusName;
	private Date insertTime;
	
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
}
