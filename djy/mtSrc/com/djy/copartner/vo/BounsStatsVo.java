package com.djy.copartner.vo;

import com.djy.bonus.model.BonusStats;
import com.frame.base.web.vo.BaseVo;

public class BounsStatsVo extends BaseVo{

	private String copartnerName;
	private Double totalCstmConsume = 0d;
	private Double bonus = 0d;
	private String insertTime;
	private String info;
	public String getCopartnerName() {
		return copartnerName;
	}
	public void setCopartnerName(String copartnerName) {
		this.copartnerName = copartnerName;
	}
	public Double getTotalCstmConsume() {
		return totalCstmConsume;
	}
	public void setTotalCstmConsume(Double totalCstmConsume) {
		this.totalCstmConsume = totalCstmConsume;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
	public static BounsStatsVo transfer(BonusStats bonusStats) {
		
		return new BounsStatsVo();
		
	}
}
