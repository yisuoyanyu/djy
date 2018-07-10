package com.djy.admin.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.sys.model.SysEmpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.vo.BaseVo;

public class BonusDayStatsVo extends BaseVo {

	private Date statsDate;
	private String emplID;
	private String emplName;
	private Double totalCstmConsume;
	private Double bonus;
	
	
	public Date getStatsDate() {
		return statsDate;
	}
	public void setStatsDate(Date statsDate) {
		this.statsDate = statsDate;
	}
	
	
	public String getEmplID() {
		return emplID;
	}
	public void setEmplID(String emplID) {
		this.emplID = emplID;
	}
	
	
	public String getEmplName() {
		return emplName;
	}
	public void setEmplName(String emplName) {
		this.emplName = emplName;
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
	
	
	
	public static BonusDayStatsVo transfer(BonusDayStatsBo bo) {
		BonusDayStatsVo vo = new BonusDayStatsVo();
		vo.copyProperity( bo );
		
		SysEmpl sysEmpl = bo.getSysEmpl();
		vo.setEmplID(sysEmpl.getEmplID());
		vo.setEmplName(sysEmpl.getRealName());
		
		vo.setStatsDate(DateUtil.toDate(DateUtil.formatDate(bo.getStatsDate(), "yyyy-MM-dd")));
		return vo;
	}


	public static List<BonusDayStatsVo> transferList(List<BonusDayStatsBo> bos) {
		List<BonusDayStatsVo> vos = new ArrayList<>();
		
		for (BonusDayStatsBo bo : bos) {
			vos.add(transfer(bo));
		}
		
		return vos;
	}
	

}
