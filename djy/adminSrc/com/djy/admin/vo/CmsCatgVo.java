package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.List;

import com.djy.cms.model.CmsCatg;
import com.frame.base.web.vo.BaseVo;

public class CmsCatgVo extends BaseVo{
	
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer status;
	private String statusName;
	private Boolean isParent;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
	public static CmsCatgVo transfer(CmsCatg cmsCatg) {
		CmsCatgVo vo = new CmsCatgVo();
		vo.copyProperity( cmsCatg );
		
		vo.setIsParent( cmsCatg.getIsParent() == null ? false : cmsCatg.getIsParent() );
		CmsCatg parent = cmsCatg.getParentCmsCatg();
		if ( parent != null )
			vo.setParentId(parent.getId());
		
		return vo;
	}
	
	public static List<CmsCatgVo> transferList(List<CmsCatg>  cmsCatgs) {
		List<CmsCatgVo> vos = new ArrayList<>();
		
		for (CmsCatg cmsCatg : cmsCatgs) {
			vos.add(transfer(cmsCatg));
		}
		
		return vos;
	}

}
