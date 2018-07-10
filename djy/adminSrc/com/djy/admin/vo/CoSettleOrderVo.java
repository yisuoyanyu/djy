package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSettleOrder;
import com.frame.base.web.vo.BaseVo;

public class CoSettleOrderVo extends BaseVo {
	
	private Integer id;
	private String no;
	private String coPartnerName;
	private Double amount;
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
	
	
	public static CoSettleOrderVo transfer(CoSettleOrder coSettleOrder) {
		CoSettleOrderVo vo = new CoSettleOrderVo();
		vo.copyProperity( coSettleOrder );
		
		CoPartner coPartner = coSettleOrder.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(coPartner.getName());
		}
		
		return vo;
	}
	
	public static List<CoSettleOrderVo> transferList(List<CoSettleOrder> coSettleOrders) {
		List<CoSettleOrderVo> vos = new ArrayList<>();
		
		for ( CoSettleOrder coSettleOrder : coSettleOrders ) {
			vos.add( transfer( coSettleOrder ) );
		}
		
		return vos;
	}
	
	
}
