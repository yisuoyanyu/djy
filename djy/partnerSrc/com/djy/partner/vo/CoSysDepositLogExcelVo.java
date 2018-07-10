package com.djy.partner.vo;

import java.util.Date;
import com.frame.base.web.vo.BaseVo;

public class CoSysDepositLogExcelVo extends BaseVo{
	
	private String no;
	private String typeName;
	private String incomeExpenseName;
	private Double amount;
	private Double sysDeposit;
	private Date insertTime;
	

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getIncomeExpenseName() {
		return incomeExpenseName;
	}

	public void setIncomeExpenseName(String incomeExpenseName) {
		this.incomeExpenseName = incomeExpenseName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSysDeposit() {
		return sysDeposit;
	}

	public void setSysDeposit(Double sysDeposit) {
		this.sysDeposit = sysDeposit;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
}
