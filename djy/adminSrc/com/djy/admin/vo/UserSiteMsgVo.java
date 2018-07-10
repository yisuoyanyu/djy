package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.user.model.UserSiteMsg;
import com.frame.base.web.vo.BaseVo;

public class UserSiteMsgVo extends BaseVo {
	
	private Integer id;
	private String typeName;
	private String content;
	private Date insertTime;
	private Date readTime;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	

	//----------------------------------------

	public static UserSiteMsgVo transfer(UserSiteMsg userSiteMsg) {
		UserSiteMsgVo vo = new UserSiteMsgVo();
		vo.copyProperity( userSiteMsg );
		
		return vo;
	}
	
	public static List<UserSiteMsgVo> transferList(List<UserSiteMsg> userSiteMsgs) {
		List<UserSiteMsgVo> vos = new ArrayList<>();
		
		for ( UserSiteMsg userSiteMsg : userSiteMsgs ) {
			vos.add( transfer( userSiteMsg ) );
		}
		
		return vos;
	}
	
	
}
