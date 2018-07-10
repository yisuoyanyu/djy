package com.djy.bonus.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.bonus.dao.BonusDayStatsDao;
import com.djy.user.model.User;
import com.frame.base.dao.impl.BaseDaoImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("bonusDayStatsDao")
public class BonusDayStatsDaoImpl extends BaseDaoImpl<BonusDayStatsBo> implements BonusDayStatsDao {
	
	
	@Override
	public List<BonusDayStatsBo> searchBonusStats(PagingBean pb, Map<String, Object> param) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String hql = "SELECT new map(stats.sysEmpl AS sysEmpl, stats.statsDate AS statsDate, SUM(stats.totalCstmConsume) AS totalCstmConsume, SUM(stats.bonus) AS bonus)" 
				+ " FROM BonusStats stats WHERE 1=1 " ;
		
		Integer sysEmplId = (Integer)param.get("sysEmplId");
		if ( !StringUtil.isEmpty( sysEmplId ) ) {
			hql += " AND stats.sysEmpl.id =:sysEmplId ";
			params.put("sysEmplId", sysEmplId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			hql += " AND stats.statsDate >=:startTime ";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			hql += " AND stats.statsDate <=:endTime ";
			params.put("endTime", endTime);
		}
		
		hql += " GROUP BY stats.statsDate ";
		
		if ( StringUtil.isEmpty( sysEmplId ) ) {
			hql += " , stats.sysEmpl ";
		}
		
		hql += " ORDER BY stats.statsDate DESC" ;
		
		List<Map<?, ?>> maps = (List<Map<?, ?>>) this.findGeneric(hql, params);
		
		List<BonusDayStatsBo> bos = BonusDayStatsBo.transferList(maps);
		
		return bos;
	}
	
	
	@Override
	public List<BonusDayStatsBo> searchAllBonusStats(PagingBean pb, Map<String, Object> param) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT bonus.sys_empl_ID AS SysEmplId, bonus.statsDate AS StatsDate, SUM(TotalCstmConsume) AS TotalCstmConsume, SUM(Bonus) AS Bonus"
				+ " FROM bonus_stats bonus WHERE 1=1";
		
		Integer sysEmplId = (Integer)param.get("sysEmplId");
		if ( !StringUtil.isEmpty( sysEmplId ) ) {
			sql += " AND bonus.sys_empl_ID =:sysEmplId";
			params.put("sysEmplId", sysEmplId);
		}
		
		Date startTime = (Date)param.get("timeStart");
		if ( !StringUtil.isEmpty( startTime ) ) {
			sql += " AND bonus.statsDate >= :startTime";
			params.put("startTime", startTime);
		}
		
		Date endTime = (Date)param.get("timeEnd");
		if ( !StringUtil.isEmpty( endTime ) ) {
			sql += " AND bonus.statsDate <= :endTime";
			params.put("endTime", endTime);
		}
		
		sql += " GROUP BY bonus.statsDate, bonus.sys_empl_ID ORDER BY StatsDate DESC ";
		
		List<BonusDayStatsBo> bos = this.findBySql(sql, params, pb);
		
		return bos;
	}
	
	
	@Override
	public List<BonusDayStatsBo> getDayTotalByUser(User user,Date startTime,Date endTime) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date startAccTime = DateUtil.getFirstTimeOfDate(startTime);
		Date endAccTime = DateUtil.getLastTimeOfDate(endTime);
	
		String hql = "SELECT new map(stats.sysEmpl AS sysEmpl, stats.statsDate AS statsDate, SUM(stats.totalCstmConsume) AS totalCstmConsume, SUM(stats.bonus) AS bonus)" 
				+ " FROM BonusStats stats" 
				+ " WHERE stats.sysEmpl.id= "+user.getSysEmpl().getId()
				+ " AND stats.statsDate >=:startAccTime"
				+ " AND stats.statsDate <=:endAccTime"
				+ " GROUP BY stats.statsDate ORDER BY stats.statsDate DESC" ;
		
		params.put("startAccTime", startAccTime);
		params.put("endAccTime", endAccTime);
		
		List<Map<?, ?>> maps = (List<Map<?, ?>>) this.findGeneric(hql, params);
		
		List<BonusDayStatsBo> bos = BonusDayStatsBo.transferList(maps);
	
		return bos;
	}
	
	
	@Override
	public List<BonusDayStatsBo> getMonthTotalByUser(User user, Date startTime, Date endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		Date startAccTime = DateUtil.getFirstTimeOfDate(startTime);
		Date endAccTime = DateUtil.getLastTimeOfDate(endTime);
	
		String hql = "SELECT new map(stats.sysEmpl AS sysEmpl, stats.statsDate AS statsDate, SUM(stats.totalCstmConsume) AS totalCstmConsume, SUM(stats.bonus) AS bonus)" 
				+ " FROM BonusStats stats" 
				+ " WHERE stats.sysEmpl.id= "+user.getSysEmpl().getId()
				+ " AND stats.statsDate >=:startAccTime"
				+ " AND stats.statsDate <=:endAccTime"
				+ " GROUP BY month(stats.statsDate) ORDER BY stats.statsDate DESC" ;
		
		params.put("startAccTime", startAccTime);
		params.put("endAccTime", endAccTime);
		
		List<Map<?, ?>> maps = (List<Map<?, ?>>) this.findGeneric(hql, params);
		
		List<BonusDayStatsBo> bos = BonusDayStatsBo.transferList(maps);
		
		return bos;
	}
	
	
}
