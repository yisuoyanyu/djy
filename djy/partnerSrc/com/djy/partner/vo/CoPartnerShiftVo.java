package com.djy.partner.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartnerShift;
import com.frame.base.web.vo.BaseVo;

public class CoPartnerShiftVo extends BaseVo {
	
	private Integer id;
	private Double totalCstmConsume;
	private Double totalCstmPay;
	private Double totalCstmSettle;
	private Double totalSysDepositPay;
	private Double totalSysDeposit;
	private Double startSysDeposit;
	private Double endSysDeposit;
	private Date startTime;
	private Date endTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getTotalCstmConsume() {
		return totalCstmConsume;
	}
	public void setTotalCstmConsume(Double totalCstmConsume) {
		this.totalCstmConsume = totalCstmConsume;
	}
	public Double getTotalCstmPay() {
		return totalCstmPay;
	}
	public Double getTotalCstmSettle() {
		return totalCstmSettle;
	}
	public void setTotalCstmSettle(Double totalCstmSettle) {
		this.totalCstmSettle = totalCstmSettle;
	}
	public Double getTotalSysDepositPay() {
		return totalSysDepositPay;
	}
	public void setTotalSysDepositPay(Double totalSysDepositPay) {
		this.totalSysDepositPay = totalSysDepositPay;
	}
	public Double getTotalSysDeposit() {
		return totalSysDeposit;
	}
	public void setTotalSysDeposit(Double totalSysDeposit) {
		this.totalSysDeposit = totalSysDeposit;
	}
	public Double getStartSysDeposit() {
		return startSysDeposit;
	}
	public void setStartSysDeposit(Double startSysDeposit) {
		this.startSysDeposit = startSysDeposit;
	}
	public Double getEndSysDeposit() {
		return endSysDeposit;
	}
	public void setEndSysDeposit(Double endSysDeposit) {
		this.endSysDeposit = endSysDeposit;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setTotalCstmPay(Double totalCstmPay) {
		this.totalCstmPay = totalCstmPay;
	}
	
	//----------------------------------------
	
	public static CoPartnerShiftVo transfer(CoPartnerShift coPartnerShift) {
		CoPartnerShiftVo vo = new CoPartnerShiftVo();
		vo.copyProperity( coPartnerShift );
		
		
		return vo;
	}
	
	public static List<CoPartnerShiftVo> transferList(List<CoPartnerShift> coPartnerShifts) {
		List<CoPartnerShiftVo> vos = new ArrayList<>();
		
		for ( CoPartnerShift coPartnerShift : coPartnerShifts ) {
			vos.add( transfer( coPartnerShift ) );
		}
		
		return vos;
	}
	
	
}
