package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.frame.base.web.vo.BaseVo;

public class CoPartnerAdVo extends BaseVo{
	
	private Integer id;
	private String coPartnerName;
	private String title;
	private String imgPath;
	private String url;
	private String choiceName;
	private String statusName;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getChoiceName() {
		return choiceName;
	}
	public void setChoiceName(String choiceName) {
		this.choiceName = choiceName;
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
	
	
	public static CoPartnerAdVo transfer(CoPartnerAd coPartnerAd) {
		CoPartnerAdVo vo = new CoPartnerAdVo();
		vo.copyProperity( coPartnerAd );
		
		if (coPartnerAd.getIsChoice()) {
			vo.setChoiceName("是");
		} else {
			vo.setChoiceName("否");
		}
		
		CoPartner coPartner = coPartnerAd.getCoPartner();
		if (coPartner != null) {
			vo.setCoPartnerName(coPartner.getName());
		}
		
		return vo;
	}
	
	public static List<CoPartnerAdVo> transferList(List<CoPartnerAd>  coPartnerAds) {
		List<CoPartnerAdVo> vos = new ArrayList<>();
		if (coPartnerAds != null) {
			for (CoPartnerAd coPartnerAd : coPartnerAds) {
				vos.add(transfer(coPartnerAd));
			}
		}
		
		return vos;
	}

}
