package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerTag;
import com.frame.base.web.vo.BaseVo;

public class CoPartnerTagVo extends BaseVo{
	private Integer id;
	private String coPartnerName;
	private String typeName;
	private String title;
	private Integer sortNumber;
	
	
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public static CoPartnerTagVo transfer(CoPartnerTag coPartnerTag) {
		CoPartnerTagVo vo = new CoPartnerTagVo();
		vo.copyProperity( coPartnerTag );
		CoPartner coPartner = coPartnerTag.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(coPartner.getName());
		}
		
		return vo;
	}
	
	public static List<CoPartnerTagVo> transferList(List<CoPartnerTag> coPartnerTags) {
		List<CoPartnerTagVo> vos = new ArrayList<>();
		
		for ( CoPartnerTag coPartnerTag : coPartnerTags ) {
			vos.add( transfer( coPartnerTag ) );
		}
		
		return vos;
	}
}
