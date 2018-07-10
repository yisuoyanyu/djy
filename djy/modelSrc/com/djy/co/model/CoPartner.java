package com.djy.co.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.sys.model.SysEmpl;
import com.djy.user.model.User;
import com.frame.base.model.BaseModel;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;



@Entity
@Table(name = "co_partner")
@org.hibernate.annotations.Table(appliesTo="co_partner", comment="合作商家")
public class CoPartner extends BaseModel {
	
	private User user;

	private String name;
	
	private String slogan;
	private String logoPath;
	
	private String province;
	private String city;
	private String county;
	private String town;
	private String address;
	private String telephone;
	private String contact;
	private String contactMobile;

	private String lon;					// 经度
	private String lat;					// 维度
	
	private String businessUicenseNo;	// 合作商家营业执照号
	
	private Integer status;
	
	private Date insertTime;
	
	private Integer coMode;
	private Double SysSettlePercent;
	private Double MaxSysDeposit;
	private Double MinSysDeposit;
	
	private String password;
	
	private SysEmpl sysEmpl;
	
	private CoPartnerAccount coPartnerAccount;
	private List<CoPartnerImg> coPartnerImgs;
	private List<CoPartnerTag> coPartnerTags;
	private List<CoPartnerAd> coPartnerAds;
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "user_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '用户ID'" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Column(
			name = "Name", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '商家名称'" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(
			name = "Slogan", 
			length = 128, 
			columnDefinition = "VARCHAR(18) COMMENT '广告语'" )
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	
	@Column(
			name = "LogoPath", 
			length = 255, 
			columnDefinition = "VARCHAR(18) COMMENT 'Logo图片相对路径'" )
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	@Transient
	public String getLogoSrc() {
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		if ( StringUtil.isEmpty( fssApiSer ) ) {
			return logoPath;
		} else {
			return fssApiSer + ConfigUtil.get("sys.fssClient.accessKeyId") + logoPath;
		}
	}
	@Transient
	public String getLogoThumb() {
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		if ( StringUtil.isEmpty( fssApiSer ) ) {
			return logoPath;
		} else {
			return fssApiSer + ConfigUtil.get("sys.fssClient.accessKeyId") + logoPath + "?thumb=1";
		}
	}
	
	
	@Column(
			name = "Province", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '省份'" )
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
	@Column(
			name = "City", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '城市'" )
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	@Column(
			name = "County", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '县/区'" )
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	
	@Column(
			name = "Town", 
			length = 50, 
			columnDefinition = "VARCHAR(50) COMMENT '城镇/街道'" )
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	
	
	@Column(
			name = "Address", 
			length = 120, 
			columnDefinition = "VARCHAR(120) COMMENT '其他地址'" )
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Column(
			name = "Telephone", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '座机'" )
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	@Column(
			name = "Contact", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '联系人'" )
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	@Column(
			name = "ContactMobile", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '联系人手机'" )
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	
	@Column(
			name = "Lon", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '经度'" )
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	
	@Column(
			name = "Lat", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '纬度'" )
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
	@Column(
			name = "BusinessUicenseNo", 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '营业执照号'" )
	public String getBusinessUicenseNo() {
		return businessUicenseNo;
	}
	public void setBusinessUicenseNo(String businessUicenseNo) {
		this.businessUicenseNo = businessUicenseNo;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态：0—未填资料，1—待审核，2—正常，3—禁用'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	@Column(
			name = "CoMode", 
			columnDefinition = "TINYINT(1) COMMENT '合作方式。1—每单结算（perOrder），2—预存金额（sysDeposit）'" )
	public Integer getCoMode() {
		return coMode;
	}
	public void setCoMode(Integer coMode) {
		this.coMode = coMode;
	}
	@Transient
	public String getCoModeCode() {
		return CoPartnerMode.fromId( coMode ).name();
	}
	
	
	@Column(
			name = "SysSettlePercent", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '平台结算折扣百分比'" )
	public Double getSysSettlePercent() {
		return SysSettlePercent;
	}
	public void setSysSettlePercent(Double sysSettlePercent) {
		SysSettlePercent = sysSettlePercent;
	}
	
	
	@Column(
			name = "MaxSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '平台最高预存限额'" )
	public Double getMaxSysDeposit() {
		return MaxSysDeposit;
	}
	public void setMaxSysDeposit(Double maxSysDeposit) {
		MaxSysDeposit = maxSysDeposit;
	}
	
	
	@Column(
			name = "MinSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '平台最低预存限额'" )
	public Double getMinSysDeposit() {
		return MinSysDeposit;
	}
	public void setMinSysDeposit(Double minSysDeposit) {
		MinSysDeposit = minSysDeposit;
	}
	
	@Column(
			name = "Password", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '密码'" )
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "sys_empl_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '所属职员ID'" )
	public SysEmpl getSysEmpl() {
		return sysEmpl;
	}
	public void setSysEmpl(SysEmpl sysEmpl) {
		this.sysEmpl = sysEmpl;
	}
	
	
	@OneToOne(mappedBy="coPartner", fetch=FetchType.LAZY)
	public CoPartnerAccount getCoPartnerAccount() {
		return coPartnerAccount;
	}
	public void setCoPartnerAccount(CoPartnerAccount coPartnerAccount) {
		this.coPartnerAccount = coPartnerAccount;
	}
	
	
	@OneToMany(mappedBy = "coPartner", fetch = FetchType.LAZY)
	public List<CoPartnerImg> getCoPartnerImgs() {
		return coPartnerImgs;
	}
	public void setCoPartnerImgs(List<CoPartnerImg> coPartnerImgs) {
		this.coPartnerImgs = coPartnerImgs;
	}
	
	
	@OneToMany(mappedBy = "coPartner", fetch = FetchType.LAZY)
	public List<CoPartnerTag> getCoPartnerTags() {
		return coPartnerTags;
	}
	public void setCoPartnerTags(List<CoPartnerTag> coPartnerTags) {
		this.coPartnerTags = coPartnerTags;
	}
	
	
	@OneToMany(mappedBy = "coPartner", fetch = FetchType.LAZY)
	public List<CoPartnerAd> getCoPartnerAds() {
		return coPartnerAds;
	}
	public void setCoPartnerAds(List<CoPartnerAd> coPartnerAds) {
		this.coPartnerAds = coPartnerAds;
	}
	
	
}
