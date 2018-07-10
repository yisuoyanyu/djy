package com.djy.sys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.frame.base.model.BaseModel;

@Entity
@Table(name = "sys_resource")
@org.hibernate.annotations.Table(appliesTo = "sys_resource", comment = "资源")
public class SysResource extends BaseModel {
	
	private String name;
	private String code;
	
	private Set<SysRole> sysRoles = new HashSet<SysRole>();
	
	
	@Column(
			name = "Name", 
			nullable = false, 
			length = 32, 
			columnDefinition = "VARCHAR(32) COMMENT '资源名称'" )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(
			name = "Code", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '资源唯一标识符'" )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(
		name= "sys_role_res", 
		joinColumns = { @JoinColumn(name = "sys_resource_ID", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "sys_role_ID", nullable =false, updatable = false) }
	)
	@OrderBy("id ASC")
    public Set<SysRole> getSysRoles(){
        return sysRoles;
    }

    public void setSysRoles(Set<SysRole> sysRoles){
        this.sysRoles = sysRoles;
    }
	
	
}
