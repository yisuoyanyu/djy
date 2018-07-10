package com.djy.co.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.frame.base.model.BaseModel;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;

@Entity
@Table(name = "co_partner_img")
@org.hibernate.annotations.Table(appliesTo="co_partner_img", comment="合作商家图片")
public class CoPartnerImg extends BaseModel {

	private CoPartner coPartner;
	
	private String title;
	private String imgPath;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_partner_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '合作商家ID'" )
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	
	@Column(
			name = "Title", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '图片标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "ImgPath", 
			nullable = false, 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '图片相对路径'" )
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Transient
	public String getImgSrc() {
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		if ( StringUtil.isEmpty( fssApiSer ) ) {
			return imgPath;
		} else {
			return fssApiSer + ConfigUtil.get("sys.fssClient.accessKeyId") + imgPath;
		}
	}
	@Transient
	public String getImgThumb() {
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		if ( StringUtil.isEmpty( fssApiSer ) ) {
			return imgPath;
		} else {
			return fssApiSer + ConfigUtil.get("sys.fssClient.accessKeyId") + imgPath + "?thumb=1";
		}
	}
	
	
	
}
