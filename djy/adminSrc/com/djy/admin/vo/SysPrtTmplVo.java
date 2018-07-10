package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.sys.model.SysPrtTmpl;
import com.frame.base.web.vo.BaseVo;

public class SysPrtTmplVo extends BaseVo {
	
	private Integer id;
	private String code;
	private String title;
	private Date insertTime;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	// ------------------------------------------------------
	
	
	public static SysPrtTmplVo transfer(SysPrtTmpl sysPrtTmpl) {
		SysPrtTmplVo vo = new SysPrtTmplVo();
		vo.copyProperity( sysPrtTmpl );
		return vo;
	}
	
	
	public static List<SysPrtTmplVo> transferList(List<SysPrtTmpl> sysPrtTmpls) {
		List<SysPrtTmplVo> vos = new ArrayList<>();
		for ( SysPrtTmpl sysPrtTmpl : sysPrtTmpls) {
			vos.add( transfer(sysPrtTmpl) );
		}
		return vos;
	}
	
	
}
