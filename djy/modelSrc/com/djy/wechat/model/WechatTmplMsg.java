package com.djy.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.frame.base.model.BaseModel;


@Entity
@Table(name = "wechat_tmpl_msg")
@org.hibernate.annotations.Table(appliesTo = "wechat_tmpl_msg", comment = "微信公众号消息模板")
public class WechatTmplMsg extends BaseModel {
	
	private String toUser;
	private String templateId;
	private String url;
	private String miniProgramAppid;
	private String miniProgramPagePath;
	private String data;
	private String resultData;
	private String msgId;
	private Integer status;
	private String errMsg;
	private Date insertTime;
	
	
	
	@Column(
			name = "ToUser", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '接收者openid'" )
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	
	@Column(
			name = "TemplateId", 
			nullable = false, 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '模板ID'" )
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
	@Column(
			name = "Url", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '模板跳转链接'" )
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@Column(
			name = "MiniProgramAppid", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）'" )
	public String getMiniProgramAppid() {
		return miniProgramAppid;
	}
	public void setMiniProgramAppid(String miniProgramAppid) {
		this.miniProgramAppid = miniProgramAppid;
	}
	
	
	@Column(
			name = "MiniProgramPagePath", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '所需跳转到小程序的具体页面路径，支持带参数（示例index?foo=bar）'" )
	public String getMiniProgramPagePath() {
		return miniProgramPagePath;
	}
	public void setMiniProgramPagePath(String miniProgramPagePath) {
		this.miniProgramPagePath = miniProgramPagePath;
	}
	
	
	@Column(
			name = "Data", 
			columnDefinition = "TEXT COMMENT '模板内容键值'" )
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	@Column(
			name = "ResultData", 
			columnDefinition = "TEXT COMMENT '返回JSON数据包'" )
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	
	
	@Column(
			name = "MsgId", 
			length = 64, 
			columnDefinition = "VARCHAR(64) COMMENT '消息id'" )
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	@Column(
			name = "Status", 
			nullable = false, 
			columnDefinition = "TINYINT(1) COMMENT '状态。1—已发送，2—发送成功，3—发送失败，4—送达但失败。'" )
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	@Column(
			name = "ErrMsg", 
			length = 256, 
			columnDefinition = "VARCHAR(256) COMMENT '错误信息'" )
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
