package com.djy.partner.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.consume.model.ConsumeOrder;
import com.djy.user.model.User;
import com.frame.base.web.vo.BaseVo;

public class ConsumeOrderVo extends BaseVo {
	
	private Integer id;
	private String no;
	private String nickname;
	private String coPartnerName;
	private String mobile;
	private Double consumeAmount;
	private Double payAmount;
	private Double settleAmount;
	private String statusName;
	private String emplID;
	private Date insertTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCoPartnerName() {
		return coPartnerName;
	}
	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getEmplID() {
		return emplID;
	}
	public void setEmplID(String emplID) {
		this.emplID = emplID;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	//----------------------------------------
	
	
	public static ConsumeOrderVo transfer(ConsumeOrder consumeOrder) {
		ConsumeOrderVo vo = new ConsumeOrderVo();
		vo.copyProperity( consumeOrder );
		
		vo.setCoPartnerName(consumeOrder.getCoPartner().getName());
		
		User user = consumeOrder.getUser();
		if (user != null) {
			vo.setNickname(user.getWechatUser().getNickname());
			vo.setMobile(user.getMobile());
		}
		if (consumeOrder.getCoSettleOrder() != null) {
			vo.setSettleAmount(consumeOrder.getCoSettleOrder().getAmount());
		}
		if (consumeOrder.getCoPartnerEmpl() != null) {
			vo.setEmplID(consumeOrder.getCoPartnerEmpl().getEmplID());
		}
		return vo;
	}
	
	public static List<ConsumeOrderVo> transferList(List<ConsumeOrder> consumeOrders) {
		List<ConsumeOrderVo> vos = new ArrayList<>();
		
		for ( ConsumeOrder consumeOrder : consumeOrders ) {
			vos.add( transfer( consumeOrder ) );
		}
		
		return vos;
	}
	
	
}
