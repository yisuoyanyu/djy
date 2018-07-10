package com.djy.bonus.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.sys.model.SysEmpl;


public class BonusDayStatsBo {
	
	private Integer sysEmplId;
	private SysEmpl sysEmpl;
	private Date statsDate;
	private Double totalCstmConsume;
	private Double bonus;
	
	
	
	public Integer getSysEmplId() {
		return sysEmplId;
	}
	public void setSysEmplId(Integer sysEmplId) {
		this.sysEmplId = sysEmplId;
	}
	
	
	public SysEmpl getSysEmpl() {
		return sysEmpl;
	}
	public void setSysEmpl(SysEmpl sysEmpl) {
		this.sysEmpl = sysEmpl;
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
	
	
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	
	public static BonusDayStatsBo transfer(Map<?, ?> map) {
		try {
			BonusDayStatsBo vo = new BonusDayStatsBo();
			org.apache.commons.beanutils.BeanUtils.populate(vo, map);
			return vo;
		} catch(Exception e) {
			return null;
		}
	}
	
	public static List<BonusDayStatsBo> transferList(List<Map<?,?>> maps) {
		
		List<BonusDayStatsBo> bos = new ArrayList<>();
		
		for (Map<?, ?> map : maps) {
			bos.add(transfer(map));
		}
		
		return bos;
		
	}
	
	

}
