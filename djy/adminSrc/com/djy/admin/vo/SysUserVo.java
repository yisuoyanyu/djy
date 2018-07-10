package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.sys.model.SysRole;
import com.djy.sys.model.SysUser;
import com.frame.base.web.vo.BaseVo;

public class SysUserVo extends BaseVo {
	
	private Integer id;
	private String username;
	private String realName;
	private String mobile;
	private Integer status;
	private String statusName;
	private Date insertTime;
	private Date lastTime;
	private String lastIP;
	private Integer sysRoleId;
	private String sysRoleName;
	private String sysRoleCode;
	
	
	
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
	
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	
	
	public Integer getSysRoleId() {
		return sysRoleId;
	}
	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	
	
	public String getSysRoleName() {
		return sysRoleName;
	}
	public void setSysRoleName(String sysRoleName) {
		this.sysRoleName = sysRoleName;
	}
	
	
	public String getSysRoleCode() {
		return sysRoleCode;
	}
	public void setSysRoleCode(String sysRoleCode) {
		this.sysRoleCode = sysRoleCode;
	}
	
	
	
	public static SysUserVo transfer(SysUser sysUser) {
		SysUserVo vo = new SysUserVo();
		vo.copyProperity( sysUser );
		
		SysRole sysRole = sysUser.getSysRole();
		vo.setSysRoleId( sysRole.getId() );
		vo.setSysRoleName( sysRole.getName() );
		vo.setSysRoleCode( sysRole.getCode() );
		
		return vo;
	}
	
	public static List<SysUserVo> transferList(List<SysUser> sysUsers) {
		List<SysUserVo> vos = new ArrayList<>();
		
		for ( SysUser sysUser : sysUsers ) {
			vos.add( transfer( sysUser ) );
		}
		
		return vos;
	}
	
	
}
