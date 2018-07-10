package com.frame.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * domain类的父类，所有domain都要继承这个类
 * 
 */
@MappedSuperclass
public class BaseModel implements Serializable {
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	@Id
	@GeneratedValue
	@Column(
			name = "ID", 
			unique = true, 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED AUTO_INCREMENT COMMENT '自动编号'" 
	)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
