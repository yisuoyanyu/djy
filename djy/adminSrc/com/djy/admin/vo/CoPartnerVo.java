package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.user.model.User;
import com.frame.base.web.vo.BaseVo;

public class CoPartnerVo extends BaseVo{
	private Integer id;
	private String name;
	private String mobile;
	private String contact;
	private String contactMobile;
	private String businessUicenseNo;
	private String statusName;
	private Date insertTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getBusinessUicenseNo() {
		return businessUicenseNo;
	}

	public void setBusinessUicenseNo(String businessUicenseNo) {
		this.businessUicenseNo = businessUicenseNo;
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

	public static CoPartnerVo transfer(CoPartner coPartner) {
		CoPartnerVo vo = new CoPartnerVo();
		vo.copyProperity( coPartner );
		User user = coPartner.getUser();
		if (user != null) {
			vo.setMobile(user.getMobile());
		}
		
		vo.setStatusName(CoPartnerStatus.fromId(coPartner.getStatus()).getValue());
		
		return vo;
	}
	
	public static List<CoPartnerVo> transferList(List<CoPartner> coPartners) {
		List<CoPartnerVo> vos = new ArrayList<>();
		
		for ( CoPartner coPartner : coPartners ) {
			vos.add( transfer( coPartner ) );
		}
		
		return vos;
	}
}
