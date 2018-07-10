package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.user.model.User;
import com.frame.base.web.vo.BaseVo;


public class UserVo extends BaseVo{
	private Integer id;
	private String username;
	private String mobile;
	private String statusName;
	private String typeName;
	private Date insertTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	

	public static UserVo transfer(User user) {
		UserVo vo = new UserVo();
		vo.copyProperity( user );
		vo.setUsername(user.getWechatUser().getNickname());
		
		return vo;
	}
	
	public static List<UserVo> transferList(List<User> users) {
		List<UserVo> vos = new ArrayList<>();
		
		for ( User user : users ) {
			vos.add( transfer( user ) );
		}
		
		return vos;
	}
}
