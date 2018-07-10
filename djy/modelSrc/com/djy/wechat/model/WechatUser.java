package com.djy.wechat.model;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.djy.user.model.User;
import com.frame.base.model.BaseModel;
import com.frame.base.utils.StringUtil;

@Entity
@Table(name = "wechat_user")
@org.hibernate.annotations.Table(appliesTo="wechat_user", comment="微信公众号粉丝")
public class WechatUser extends BaseModel {
	
	private String openid;
	private Integer subscribe;
	private String nickname;
	private Integer sex;
	private String province;
	private String city;
	private String country;
	private String address;
	private String headimgUrl;
	private Long subscribeTime;
	private String remark;
	private String groupID;
	private Date insertTime;
	
	private User user;
	
	
	
	@Column(
			name = "OpenId", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT 'openid'" )
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	@Column(
			name = "Subscribe", 
			nullable = false,
			columnDefinition = "TINYINT(1) COMMENT '0—取消关注，1—关注。'" )
	public Integer getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	
	
	@Column(
			name = "Nickname", 
			nullable = false,
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '昵称'" )
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	@Column(
			name = "Sex",
			columnDefinition = "TINYINT(1) COMMENT '性别。1—男，2—女，0—未知。'" )
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column(
			name = "Province", 
			length = 50, 
			columnDefinition = "VARCHAR(50) COMMENT '用户个人资料填写的省份'" )
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	
	@Column(
			name = "City", 
			length = 50, 
			columnDefinition = "VARCHAR(50) COMMENT '用户个人资料填写的城市'" )
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	@Column(
			name = "Country", 
			length = 50, 
			columnDefinition = "VARCHAR(50) COMMENT '国家，如中国为CN。'" )
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	@Column(
			name = "Address", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '地址'" )
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Column(
			name = "HeadimgUrl", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。'" )
	public String getHeadimgUrl() {
		return headimgUrl;
	}
	public void setHeadimgUrl(String headimgUrl) {
		this.headimgUrl = headimgUrl;
	}
	
	
	@Column(
			name = "SubscribeTime", 
			nullable = false, 
			columnDefinition = "BIGINT(20) COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间。'" )
	public Long getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(Long subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	@Transient
    public String getSubtime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(subscribeTime);
        return sdf.format(date);
    }
	
	
	@Column(
			name = "Remark", 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注'" )
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	@Column(
			name = "GroupID",
			length = 16,
			columnDefinition = "VARCHAR(16) COMMENT '用户所在的分组ID'" )
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
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
	
	 @Transient
	    public String getNickNameStar() {
	        return StringUtil.formatNameToStar( nickname );
	    }
	
}
