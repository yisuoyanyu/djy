package com.djy.empl.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.bonus.model.BonusStats;
import com.djy.co.model.CoPartner;
import com.djy.sys.model.SysEmpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.vo.BaseVo;

public class BonusStatsVo extends BaseVo{
	
	private Integer id;
	private String coPartnerName;
	private Date statsDate;
	private Double totalCstmConsume;
	private Double maxSysDeposit;
	private Double indexPercent;
	private Double bonusPercent;
	private Double bonus;
	private String sysEmplName;
	private Date insertTime;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCoPartnerName() {
		return coPartnerName;
	}


	public void setCoPartnerName(String coPartnerName) {
		this.coPartnerName = coPartnerName;
	}


	public Date getStatsDate() {
		return statsDate;
	}


	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}


	public Double getTotalCstmConsume() {
		return totalCstmConsume;
	}


	public void setTotalCstmConsume(Double totalCstmConsume) {
		this.totalCstmConsume = totalCstmConsume;
	}


	public Double getMaxSysDeposit() {
		return maxSysDeposit;
	}


	public void setMaxSysDeposit(Double maxSysDeposit) {
		this.maxSysDeposit = maxSysDeposit;
	}


	public Double getIndexPercent() {
		return indexPercent;
	}


	public void setIndexPercent(Double indexPercent) {
		this.indexPercent = indexPercent;
	}


	public Double getBonusPercent() {
		return bonusPercent;
	}


	public void setBonusPercent(Double bonusPercent) {
		this.bonusPercent = bonusPercent;
	}


	public Double getBonus() {
		return bonus;
	}


	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}


	public String getSysEmplName() {
		return sysEmplName;
	}


	public void setSysEmplName(String sysEmplName) {
		this.sysEmplName = sysEmplName;
	}


	public Date getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}


	public static BonusStatsVo transfer(BonusStats bonusStats) {
		BonusStatsVo vo = new BonusStatsVo();
		vo.copyProperity( bonusStats );
		
		CoPartner coPartner = bonusStats.getCoPartner();
		vo.setCoPartnerName(coPartner.getName());
		
		SysEmpl sysEmpl = bonusStats.getSysEmpl();
		vo.setSysEmplName(sysEmpl.getEmplID());
		//获取到的日期需转换
		vo.setStatsDate(DateUtil.toDate(DateUtil.formatDate(bonusStats.getStatsDate(), "yyyy-MM-dd")));
		return vo;
	}


	public static List<BonusStatsVo> transferList(List<BonusStats>  bonusStatses) {
		List<BonusStatsVo> vos = new ArrayList<>();
		
		for (BonusStats bonusStats : bonusStatses) {
			vos.add(transfer(bonusStats));
		}
		
		return vos;
	}
	
}
