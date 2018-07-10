package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.consume.model.ConsumeOrder;
import com.djy.spread.model.SpreadCommission;
import com.djy.spread.model.SpreadPromoter;
import com.frame.base.web.vo.BaseVo;

public class SpreadCommissionVo extends BaseVo{
	
	private Integer id;
	private String realName;
	private String mobile;
	private String no;
	private Double amount;
	private String statusName;
	private String consumeOrderNo;
	private Date insertTime;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	
	
	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


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


	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public String getConsumeOrderNo() {
		return consumeOrderNo;
	}


	public void setConsumeOrderNo(String consumeOrderNo) {
		this.consumeOrderNo = consumeOrderNo;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public static SpreadCommissionVo transfer(SpreadCommission spreadCommission) {
		SpreadCommissionVo vo = new SpreadCommissionVo();
		vo.copyProperity( spreadCommission );
		
		SpreadPromoter spreadPromoter = spreadCommission.getSpreadPromoter();
		if (spreadPromoter != null) {
			vo.setRealName(spreadPromoter.getRealName());
			vo.setMobile(spreadPromoter.getUser().getMobile());
		}
		
		ConsumeOrder consumeOrder = spreadCommission.getConsumeOrder();
		if (consumeOrder != null) {
			vo.setConsumeOrderNo(consumeOrder.getNo());
		}
		
		return vo;
	}


	public static List<SpreadCommissionVo> transferList(List<SpreadCommission>  spreadCommissions) {
		List<SpreadCommissionVo> vos = new ArrayList<>();
		
		for (SpreadCommission spreadCommission : spreadCommissions) {
			vos.add(transfer(spreadCommission));
		}
		
		return vos;
	}
	
}
