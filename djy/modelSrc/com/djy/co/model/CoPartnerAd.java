package com.djy.co.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.djy.co.enumtype.CoPartnerAdStatus;
import com.frame.base.model.BaseModel;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;


@Entity
@Table(name = "co_partner_ad")
@org.hibernate.annotations.Table(appliesTo="co_partner_ad", comment="商家广告活动")
public class CoPartnerAd extends BaseModel {
	
	private CoPartner coPartner;
	
	private String title;
	private String imgPath;
	private String url;
	private Date startTime;
	private Date endTime;
	private Boolean isChoice;
	private Integer status;
	private Date insertTime;
	
	private List<CoPartnerAdTag> coPartnerAdTags;
	
	
	
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
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(16) COMMENT '标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "ImgPath", 
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
	
	
	@Column(
			name = "Url", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '点击打开对应链接'" )
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "StartTime", 
    		columnDefinition = "DATETIME COMMENT '插入时间'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "EndTime", 
    		columnDefinition = "DATETIME COMMENT '插入时间'" )
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	@Column(
			name = "IsChoice", 
			columnDefinition = "TINYINT(1) COMMENT '是否精选。1—是。'" )
	public Boolean getIsChoice() {
		return isChoice;
	}
	public void setIsChoice(Boolean isChoice) {
		this.isChoice = isChoice;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。0—禁用，1—正常。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return CoPartnerAdStatus.fromId(status).getValue();
	}
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "InsertTime", 
    		nullable = false, 
    		columnDefinition = "DATETIME COMMENT '插入时间'" )
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
	@OneToMany(mappedBy = "coPartnerAd", fetch = FetchType.LAZY)
	public List<CoPartnerAdTag> getCoPartnerAdTags() {
		return coPartnerAdTags;
	}
	public void setCoPartnerAdTags(List<CoPartnerAdTag> coPartnerAdTags) {
		this.coPartnerAdTags = coPartnerAdTags;
	}
	
	
}
