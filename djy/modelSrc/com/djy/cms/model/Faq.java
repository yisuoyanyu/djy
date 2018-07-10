package com.djy.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.frame.base.model.BaseModel;


@Entity
@Table(name = "faq")
@org.hibernate.annotations.Table(appliesTo = "faq", comment = "FAQ问答")
public class Faq extends BaseModel {

	private String question;
	private String answer;
	private Integer sortNumber;
	private Date insertTime;
	
	
	
	@Column(
			name = "Question", 
			nullable = false, 
			length = 255, 
			columnDefinition = "VARCHAR(255) COMMENT '问题'" )
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	
	@Column(
			name = "Answer", 
			nullable = false, 
			length = 5000, 
			columnDefinition = "VARCHAR(5000) COMMENT '回答'" )
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(
    		name = "InsertTime", 
    		nullable = false, 
    		columnDefinition = "DATETIME COMMENT '插入时间'" )
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
