package com.djy.sys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.frame.base.model.BaseModel;

@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Table(appliesTo = "sys_role", comment = "角色")
public class SysRole extends BaseModel {
	
	private String name;
	private String code;
	
	private Set<SysResource> sysResources = new HashSet<SysResource>();
	
	
	@Column(
			name = "Name", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '角色名称'" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(
			name = "Code", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '角色唯一标识符'" )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(
		name= "sys_role_res", 
		joinColumns = { @JoinColumn(name = "sys_role_ID", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "sys_resource_ID", nullable = false, updatable = false) }
	)
	@OrderBy("id ASC")
	public Set<SysResource> getSysResources(){
		return sysResources;
	}
	public void setSysResources(Set<SysResource> sysResources){
		this.sysResources = sysResources;
	}
	
	
}
