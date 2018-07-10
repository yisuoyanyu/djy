package com.frame.base.web.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;

public abstract class BaseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public void copyProperity(Object obj) {
		BeanUtils.copyProperties(obj, this);
	}
	
}
