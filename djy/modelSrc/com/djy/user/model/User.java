package com.djy.user.model;

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

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.consume.model.ConsumeOrder;
import com.djy.spread.model.SpreadPromoter;
import com.djy.sys.model.SysEmpl;
import com.djy.user.enumtype.UserStatus;
import com.djy.user.enumtype.UserType;
import com.djy.wechat.model.WechatUser;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "user")
@org.hibernate.annotations.Table(appliesTo="user", comment="用户")
public class User extends BaseModel {

	private Integer type;
	private String username;
	private String mobile;
	private Integer status;
	private CoPartner coPartner;
	private User sponsor;
	private String spreadCode;
	private Date insertTime;
	
	private String qrcodeAddress;
	private String qrcodeUrlStr;
	
	private CoPartner ofCoPartner;
	
	private WechatUser wechatUser;
	private UserAccount userAccount;
	
	private SysEmpl sysEmpl;
	private CoPartnerEmpl coPartnerEmpl;
	private SpreadPromoter spreadPromoter;
	
	private List<ConsumeOrder> consumeOrders;
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '类型：1 — 系统顾客，2 — 合作商家。'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return UserType.fromId( type ).getValue();
	}
	
	@Column(
			name = "Username", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '用户名'" )
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Column(
			name = "Mobile", 
			length = 16, 
			columnDefinition = "VARCHAR(16) COMMENT '手机'" )
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—正常，2—冻结。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getStatusName() {
		return UserStatus.fromId( status ).getValue();
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "Sponsor", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '保荐人、推荐人ID'" )
	public User getSponsor() {
		return sponsor;
	}
	public void setSponsor(User sponsor) {
		this.sponsor = sponsor;
	}
	
	
	@Column(
			name = "SpreadCode", 
			length = 10, 
			columnDefinition = "VARCHAR(10) COMMENT '推广码'" )
	public String getSpreadCode() {
		return spreadCode;
	}
	public void setSpreadCode(String spreadCode) {
		this.spreadCode = spreadCode;
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
			name = "QrcodeAddress", 
			nullable = true, 
			length = 250, 
			columnDefinition = "VARCHAR(250) COMMENT '二维码地址'" )
	public String getQrcodeAddress() {
		return qrcodeAddress;
	}
	public void setQrcodeAddress(String qrcodeAddress) {
		this.qrcodeAddress = qrcodeAddress;
	}
	
	
	@Column(
			name = "QrcodeUrlStr", 
			nullable = true, 
			length = 250, 
			columnDefinition = "VARCHAR(250) COMMENT '二维码url内容'" )
	public String getQrcodeUrlStr() {
		return qrcodeUrlStr;
	}
	public void setQrcodeUrlStr(String qrcodeUrlStr) {
		this.qrcodeUrlStr = qrcodeUrlStr;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "OfCoPartner", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '所属合作商家编号'" )
	public CoPartner getOfCoPartner() {
		return ofCoPartner;
	}
	public void setOfCoPartner(CoPartner ofCoPartner) {
		this.ofCoPartner = ofCoPartner;
	}
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public SysEmpl getSysEmpl() {
		return sysEmpl;
	}
	public void setSysEmpl(SysEmpl sysEmpl) {
		this.sysEmpl = sysEmpl;
	}
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public CoPartnerEmpl getCoPartnerEmpl() {
		return coPartnerEmpl;
	}
	public void setCoPartnerEmpl(CoPartnerEmpl coPartnerEmpl) {
		this.coPartnerEmpl = coPartnerEmpl;
	}
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public SpreadPromoter getSpreadPromoter() {
		return spreadPromoter;
	}
	public void setSpreadPromoter(SpreadPromoter spreadPromoter) {
		this.spreadPromoter = spreadPromoter;
	}
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public WechatUser getWechatUser() {
		return wechatUser;
	}
	public void setWechatUser(WechatUser wechatUser) {
		this.wechatUser = wechatUser;
	}
	
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<ConsumeOrder> getConsumeOrders() {
		return consumeOrders;
	}
	public void setConsumeOrders(List<ConsumeOrder> consumeOrders) {
		this.consumeOrders = consumeOrders;
	}
	
	
}
