package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.List;

import com.djy.co.model.CoPartnerAd;
import com.djy.co.model.CoPartnerAdTag;
import com.frame.base.web.vo.BaseVo;

public class CoPartnerAdTagVo extends BaseVo{
	private Integer id;
	private String coPartnerAdTitle;
	private String typeName;
	private String title;
	private Integer sortNumber;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoPartnerAdTitle() {
		return coPartnerAdTitle;
	}

	public void setCoPartnerAdTitle(String coPartnerAdTitle) {
		this.coPartnerAdTitle = coPartnerAdTitle;
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

	public static CoPartnerAdTagVo transfer(CoPartnerAdTag coPartnerAdTag) {
		CoPartnerAdTagVo vo = new CoPartnerAdTagVo();
		vo.copyProperity( coPartnerAdTag );
		CoPartnerAd coPartnerAd = coPartnerAdTag.getCoPartnerAd();
		if (coPartnerAd != null) {
			vo.setCoPartnerAdTitle(coPartnerAd.getTitle());
		}
		
		return vo;
	}
	
	public static List<CoPartnerAdTagVo> transferList(List<CoPartnerAdTag> coPartnerAdTags) {
		List<CoPartnerAdTagVo> vos = new ArrayList<>();
		
		for ( CoPartnerAdTag coPartnerAdTag : coPartnerAdTags ) {
			vos.add( transfer( coPartnerAdTag ) );
		}
		
		return vos;
	}
}
