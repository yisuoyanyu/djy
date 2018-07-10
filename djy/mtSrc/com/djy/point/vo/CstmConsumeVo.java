package com.djy.point.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.co.enumtype.CoSysDepositLogType;
import com.djy.co.model.CoSysDepositLog;
import com.djy.consume.model.ConsumeOrder;
import com.frame.base.web.vo.BaseVo;

public class CstmConsumeVo extends BaseVo {

	private Integer type;
	private String typeName;
	private String title;
	private Double amount;
	private Double payAmount;
	private Double sysDeposit;
	private Date insertTime;
	private String info;
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	public Double getSysDeposit() {
		return sysDeposit;
	}
	public void setSysDeposit(Double sysDeposit) {
		this.sysDeposit = sysDeposit;
	}
	
	
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	//------------------------------------------------
	
	public static CstmConsumeVo transfer( ConsumeOrder consumeOrder ) {
		
		CstmConsumeVo vo = new CstmConsumeVo();
		
		vo.setType( CoSysDepositLogType.consume.getId() );
		vo.setTypeName( "consume" );
		vo.setTitle( "会员：" + consumeOrder.getUser().getWechatUser().getNickname() );
		vo.setAmount( consumeOrder.getConsumeAmount() );
		vo.setInsertTime( consumeOrder.getInsertTime() );
		if (null != consumeOrder.getCoSettleOrder()) {
			vo.setPayAmount(consumeOrder.getCoSettleOrder().getAmount());
		}
		
		return vo;
		
	}
	
	
	public static List<CstmConsumeVo> transferListByConsumeOrders( List<ConsumeOrder> consumeOrders ) {
		
		List<CstmConsumeVo> vos = new ArrayList<>();
		
		for ( ConsumeOrder consumeOrder : consumeOrders ) {
			vos.add( transfer( consumeOrder ) );
		}
		
		return vos;
		
	}
	
	
	public static CstmConsumeVo transfer( CoSysDepositLog coSysDepositLog ) {
		
		CstmConsumeVo vo = new CstmConsumeVo();
		
		if ( coSysDepositLog.getType() == CoSysDepositLogType.consume.getId() ) {
			
			vo.copyProperity( coSysDepositLog );
			vo.setTypeName( "consume" );
			vo.setTitle( "会员：" + coSysDepositLog.getConsumeOrder().getUser().getWechatUser().getNickname() );
			
		} else if ( coSysDepositLog.getType() == CoSysDepositLogType.deposit.getId() ) {
			
			vo.copyProperity( coSysDepositLog );
			vo.setTypeName( "deposit" );
			vo.setTitle( "平台" );
			
		}
		
		return vo;
		
	}
	
	
	public static List<CstmConsumeVo> transferListByCoSysDepositLogs( List<CoSysDepositLog> coSysDepositLogs ) {
		
		List<CstmConsumeVo> vos = new ArrayList<>();
		
		for ( CoSysDepositLog coSysDepositLog : coSysDepositLogs ) {
			vos.add( transfer( coSysDepositLog ) );
		}
		
		return vos;
		
	}
	
	public static CstmConsumeVo getCsVo(CstmConsumeVo consumeVo) {
		String time;
		String strong = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		time = sf.format(consumeVo.getInsertTime());
		
		if ("consume".equals(consumeVo.getTypeName())) {
			strong += "<strong class=\"consume\">消</strong>";
		}
		if ("deposit".equals(consumeVo.getTypeName())) {
			strong += "<strong class=\"deposit\">充</strong>";
		}
		
		String sysDeposit = "";
		
		if (null != consumeVo.getSysDeposit()) {
			sysDeposit = "<span class=\"am-fr am-text-sm gray\">余额：￥"+consumeVo.getSysDeposit()+"</span>";
		}else if (null != consumeVo.getPayAmount()) {
			sysDeposit = "<span class=\"am-fr am-text-sm gray\">实收：￥"+consumeVo.getPayAmount()+"</span>";
		}
		String info = "  <li name = \"csli\">"
	    		+"<span class=\"mark\">"
	    		+strong
	    		+"</span>"
	    		+"<div>"
    			+"<div class=\"am-cf\">"
    			+"<span class=\"am-fl\">"+consumeVo.getTitle()+"</span>"
    			+"<span class=\"am-fr red\"> "+consumeVo.getAmount()+" </span>"
    			+"</div>"
    			+"<div class=\"am-cf\">"
    			+"<span class=\"am-fl am-text-sm gray\">"
    			+time
    			+"</span>"
    			+sysDeposit
    			+"</div>"
	    		+"</div>"
	    	    +"</li>";
		
		CstmConsumeVo cstmConsumeVo = new CstmConsumeVo();
		cstmConsumeVo.setInfo(info);
		return cstmConsumeVo;
	}
}
