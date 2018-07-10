package com.djy.partner.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSysDepositOrder;
import com.frame.base.web.vo.BaseVo;

public class CoSysDepositOrderVo extends BaseVo {
	
	private Integer id;
	private String no;
	private String coPartnerName;
	private Double amount;
	private Double payAmount;
	private String statusName;
	private Date insertTime;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	public String getCoPartnerName() {
		return coPartnerName;
	}
	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
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
	
	//----------------------------------------
	
	
	public static CoSysDepositOrderVo transfer(CoSysDepositOrder coSysDepositOrder) {
		CoSysDepositOrderVo vo = new CoSysDepositOrderVo();
		vo.copyProperity( coSysDepositOrder );
		
		CoPartner coPartner = coSysDepositOrder.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(coPartner.getName());
		}
		
		return vo;
	}
	
	public static List<CoSysDepositOrderVo> transferList(List<CoSysDepositOrder> coSysDepositOrders) {
		List<CoSysDepositOrderVo> vos = new ArrayList<>();
		
		for ( CoSysDepositOrder coSysDepositOrder : coSysDepositOrders ) {
			vos.add( transfer( coSysDepositOrder ) );
		}
		
		return vos;
	}
	
	
}
