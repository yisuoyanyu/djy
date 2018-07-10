package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.user.model.UserSmsMsg;
import com.frame.base.web.vo.BaseVo;

public class UserSmsMsgVo extends BaseVo {
	
	private Integer id;
	private String mobile;
	private String content;
	private Date insertTime;
	private Date sendTime;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	

	//----------------------------------------


	public static UserSmsMsgVo transfer(UserSmsMsg userSmsMsg) {
		UserSmsMsgVo vo = new UserSmsMsgVo();
		vo.copyProperity( userSmsMsg );
		
		return vo;
	}
	
	public static List<UserSmsMsgVo> transferList(List<UserSmsMsg> userSmsMsgs) {
		List<UserSmsMsgVo> vos = new ArrayList<>();
		
		for ( UserSmsMsg userSmsMsg : userSmsMsgs ) {
			vos.add( transfer( userSmsMsg ) );
		}
		
		return vos;
	}
	
	
}
