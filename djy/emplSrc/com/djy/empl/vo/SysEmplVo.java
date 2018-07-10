package com.djy.empl.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.sys.model.SysEmpl;
import com.frame.base.web.vo.BaseVo;

public class SysEmplVo extends BaseVo{
	
	private Integer id;
	private String emplID;
	private String realName;
	private String idcard;
	private String mobile;
	private String email;
	private Double indexPercent;
	private Double bonusPercent;
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


	public Double getIndexPercent() {
		return indexPercent;
	}


	public void setIndexPercent(Double indexPercent) {
		this.indexPercent = indexPercent;
	}


	public Double getBonusPercent() {
		return bonusPercent;
	}


	public void setBonusPercent(Double bonusPercent) {
		this.bonusPercent = bonusPercent;
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


	public static SysEmplVo transfer(SysEmpl sysEmpl) {
		SysEmplVo vo = new SysEmplVo();
		vo.copyProperity( sysEmpl );
		
		return vo;
	}


	public static List<SysEmplVo> transferList(List<SysEmpl>  sysEmpls) {
		List<SysEmplVo> vos = new ArrayList<>();
		
		for (SysEmpl sysEmpl : sysEmpls) {
			vos.add(transfer(sysEmpl));
		}
		
		return vos;
	}
	
}
