package com.djy.bonus.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.djy.co.model.CoPartner;
import com.djy.sys.model.SysEmpl;
import com.frame.base.model.BaseModel;

@Entity
@Table(name = "bonus_stats")
@org.hibernate.annotations.Table(appliesTo="bonus_stats", comment="提成统计")
public class BonusStats extends BaseModel {

	private CoPartner coPartner;
	private Date statsDate;
	private Double totalCstmConsume = 0d;
	private Double maxSysDeposit;
	private Double indexPercent;
	private Double bonusPercent;
	private Double bonus = 0d;
	private SysEmpl sysEmpl;
	private Date insertTime;
	
	
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
	
	@Temporal(TemporalType.DATE)
    @Column(
    		name = "StatsDate", 
    		nullable = false, 
    		columnDefinition = "DATE COMMENT '统计日期'" )
	public Date getStatsDate() {
		return statsDate;
	}
	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}
	
	@Column(
			name = "TotalCstmConsume", 
			nullable = false, 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '会员消费总额'" )
	public Double getTotalCstmConsume() {
		return totalCstmConsume;
	}
	public void setTotalCstmConsume(Double totalCstmConsume) {
		this.totalCstmConsume = totalCstmConsume;
	}
	
	@Column(
			name = "MaxSysDeposit", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '平台最高预存限额'" )
	public Double getMaxSysDeposit() {
		return maxSysDeposit;
	}
	public void setMaxSysDeposit(Double maxSysDeposit) {
		this.maxSysDeposit = maxSysDeposit;
	}
	
	@Column(
			name = "IndexPercent", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '指标比例，营业额占充值额的百分比。'" )
	public Double getIndexPercent() {
		return indexPercent;
	}
	public void setIndexPercent(Double indexPercent) {
		this.indexPercent = indexPercent;
	}
	
	@Column(
			name = "BonusPercent", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) COMMENT '提成比例，占营业额的百分比。'" )
	public Double getBonusPercent() {
		return bonusPercent;
	}
	public void setBonusPercent(Double bonusPercent) {
		this.bonusPercent = bonusPercent;
	}
	
	@Column(
			name = "Bonus", 
			precision=12, scale=2, 
			columnDefinition = "DOUBLE(12,2) DEFAULT 0.00 COMMENT '提成金额'" )
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = "sys_empl_ID", 
			columnDefinition = "INT(11) UNSIGNED COMMENT '提成所属职员'" )
	public SysEmpl getSysEmpl() {
		return sysEmpl;
	}
	public void setSysEmpl(SysEmpl sysEmpl) {
		this.sysEmpl = sysEmpl;
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
