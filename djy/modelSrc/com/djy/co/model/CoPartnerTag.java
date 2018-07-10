package com.djy.co.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.djy.co.enumtype.CoPartnerTagType;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "co_partner_tag")
@org.hibernate.annotations.Table(appliesTo="co_partner_tag", comment="合作商家标签")
public class CoPartnerTag extends BaseModel {

	private CoPartner coPartner;
	
	private Integer type;
	private String title;
	private Integer sortNumber;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "co_partner_ID", 
			nullable = false, 
			columnDefinition = "INT(11) UNSIGNED COMMENT '合作商家ID'" )
	public CoPartner getCoPartner() {
		return coPartner;
	}
	public void setCoPartner(CoPartner coPartner) {
		this.coPartner = coPartner;
	}
	
	
	@Column(
			name = "Type", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '标签类型。1—减，2—折，3—送。'" )
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Transient
	public String getTypeName() {
		return CoPartnerTagType.fromId(type).getValue();
	}
	
	@Column(
			name = "Title", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '标题'" )
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(
			name = "SortNumber", 
			columnDefinition = "INT(4) COMMENT '排序号'" )
	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}
	
	
}
