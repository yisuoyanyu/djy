package com.djy.partner.vo;

import java.util.Date;
import com.frame.base.web.vo.BaseVo;

public class CoSysDepositOrderExcelVo extends BaseVo {
	
	private String no;
	private Double amount;
	private Double payAmount;
	private String statusName;
	private Date insertTime;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
