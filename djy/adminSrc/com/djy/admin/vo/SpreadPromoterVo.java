package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.spread.model.SpreadPromoter;
import com.frame.base.web.vo.BaseVo;

public class SpreadPromoterVo extends BaseVo{
	
	private Integer id;
	private String emplID;
	private String realName;
	private String idcard;
	private String mobile;
	private String email;
	private Double spreadConsumeFirstComm;
	private String statusName;
	private Date insertTime;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}



	public String getEmplID() {
		return emplID;
	}


	public void setEmplID(String emplID) {
		this.emplID = emplID;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Double getSpreadConsumeFirstComm() {
		return spreadConsumeFirstComm;
	}


	public void setSpreadConsumeFirstComm(Double spreadConsumeFirstComm) {
		this.spreadConsumeFirstComm = spreadConsumeFirstComm;
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


	public static SpreadPromoterVo transfer(SpreadPromoter spreadPromoter) {
		SpreadPromoterVo vo = new SpreadPromoterVo();
		vo.copyProperity( spreadPromoter );
		
		return vo;
	}


	public static List<SpreadPromoterVo> transferList(List<SpreadPromoter>  spreadPromoters) {
		List<SpreadPromoterVo> vos = new ArrayList<>();
		
		for (SpreadPromoter spreadPromoter : spreadPromoters) {
			vos.add(transfer(spreadPromoter));
		}
		
		return vos;
	}
	
}
