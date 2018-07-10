package com.djy.partner.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.enumtype.CoSysDepositLogIncomeExpense;
import com.djy.co.enumtype.CoSysDepositLogType;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.web.vo.BaseVo;

public class CoSysDepositLogVo extends BaseVo{
	private Integer id;
	private String coPartnerName;
	private Integer type;
	private String typeName;
	private String incomeExpenseAmount;
	private Double sysDeposit;
	private String no;
	private Date insertTime;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoPartnerName() {
		return coPartnerName;
	}

	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getIncomeExpenseAmount() {
		return incomeExpenseAmount;
	}

	public void setIncomeExpenseAmount(String incomeExpenseAmount) {
		this.incomeExpenseAmount = incomeExpenseAmount;
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
	
	//----------------------------------------

	public static CoSysDepositLogVo transfer(CoSysDepositLog coSysDepositLog) {
		CoSysDepositLogVo vo = new CoSysDepositLogVo();
		vo.copyProperity( coSysDepositLog );
		
		vo.setCoPartnerName(coSysDepositLog.getCoPartner().getName());
		vo.setTypeName(CoSysDepositLogType.fromId(coSysDepositLog.getType()).getValue());
		
		ConsumeOrder consumeOrder = coSysDepositLog.getConsumeOrder();
		if (consumeOrder != null) {
			vo.setNo(consumeOrder.getNo());
		}
		CoSysDepositOrder coSysDepositOrder = coSysDepositLog.getCoSysDepositOrder();
		if (coSysDepositOrder != null) {
			vo.setNo(coSysDepositOrder.getNo());
		}
		vo.setIncomeExpenseAmount(CoSysDepositLogIncomeExpense.fromId(coSysDepositLog.getIncomeExpense()).getFlag()+coSysDepositLog.getAmount());
		
		return vo;
	}
	
	public static List<CoSysDepositLogVo> transferList(List<CoSysDepositLog> coSysDepositLogs) {
		List<CoSysDepositLogVo> vos = new ArrayList<>();
		
		for ( CoSysDepositLog coSysDepositLog : coSysDepositLogs ) {
			vos.add( transfer( coSysDepositLog ) );
		}
		
		return vos;
	}
}
