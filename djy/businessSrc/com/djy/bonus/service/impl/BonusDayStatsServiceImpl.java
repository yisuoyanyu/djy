package com.djy.bonus.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.bonus.dao.BonusDayStatsDao;
import com.djy.bonus.service.BonusDayStatsService;
import com.djy.sys.service.SysEmplService;
import com.djy.user.model.User;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("bonusDayStatsService")
public class BonusDayStatsServiceImpl extends BaseServiceImpl<BonusDayStatsBo> implements BonusDayStatsService {
	
	@Autowired
	private BonusDayStatsDao bonusDayStatsDao;
	
	@Autowired
	private SysEmplService sysEmplService;
	
	
	
	@Override
	public List<BonusDayStatsBo> searchBonusStats(PagingBean pb, Map<String, Object> param) {
		
		Date today = new Date();
		Date yesterday = DateUtil.addDay(today, -1);
		
		Date lastday = DateUtil.getLastTimeOfDate(yesterday);
		
		Date insertTimeStart = (Date) param.get("timeStart");
		Date insertTimeEnd = (Date) param.get("timeEnd");
		
		Integer sysEmplId = (Integer)param.get("sysEmplId");
		if (StringUtil.isBlank(insertTimeStart) && StringUtil.isBlank(insertTimeEnd)) {
			
			Date statrtTime = DateUtil.getFirstTimeOfDate(sysEmplService.get(sysEmplId).getInsertTime());
			long totalItems = DateUtil.diffDay(statrtTime, lastday) + 1;//总记录数；总天数减一表示总共有多少的数据
			pb.setTotalItems(totalItems);
			
			Date startDate = DateUtil.addDay(lastday, -(pb.toPage * pb.getPageSize()-1));
			Date startTime = DateUtil.getFirstTimeOfDate(startDate);
			Date endTime = DateUtil.addDay(lastday, -(pb.start));
			
			//System.out.println("startTime="+startTime+",endTime="+endTime);
			
			param.put("timeStart", startTime);
			param.put("timeEnd", endTime);
			
		} else { //按条件查询
			
			Date statsDateStart = null;
			Date statsDateEnd = null;
			
			//开始时间不为空且开始时间大于职员生成时间
			if (!StringUtil.isBlank(insertTimeStart) && DateUtil.getFirstTimeOfDate(insertTimeStart).getTime() > DateUtil.getFirstTimeOfDate(sysEmplService.get(sysEmplId).getInsertTime()).getTime()) {
				statsDateStart = insertTimeStart;
			} else {
				statsDateStart = DateUtil.getFirstTimeOfDate(sysEmplService.get(sysEmplId).getInsertTime());
			}
			//结束时间不为空且结束时间小于昨天
			if (!StringUtil.isBlank(insertTimeEnd) && insertTimeEnd.getTime() < lastday.getTime()) {
				statsDateEnd = insertTimeEnd;
			} else {
				statsDateEnd = lastday;
			}
			
			Date statrtTime = statsDateStart;
			long totalItems = DateUtil.diffDay(statrtTime, statsDateEnd) + 1;//总记录数;总天数减一表示总共有多少的数据
			pb.setTotalItems(totalItems);
			
			Date startDate = DateUtil.addDay(statsDateEnd, -(pb.toPage * pb.getPageSize()-1));
			Date startTime = DateUtil.getFirstTimeOfDate(startDate);
			
			Date endTime = DateUtil.addDay(statsDateEnd, -(pb.start));
			
			if (statsDateStart != null && startDate.getTime() < statsDateStart.getTime()) {
				startTime = statsDateStart;
			}
			//System.out.println("startTime2="+startTime+",endTime2="+endTime);
			
			param.put("timeStart", startTime);
			param.put("timeEnd", endTime);
			
		}
		
		return bonusDayStatsDao.searchBonusStats(pb,param);
	}
	
	
	@Override
	public List<BonusDayStatsBo> searchAllBonusStats(PagingBean pb, Map<String, Object> param) {
		
		Date today = new Date();
		Date yesterday = DateUtil.addDay(today, -1);
		
		Date insertTimeStart = (Date) param.get("timeStart");
		Date insertTimeEnd = (Date) param.get("timeEnd");
		
		//没有统计日期查询条件时，只查询昨天的提成统计数据
		if (StringUtil.isBlank(insertTimeStart) && StringUtil.isBlank(insertTimeEnd)) {
			
			param.put("timeStart",  DateUtil.getFirstTimeOfDate(yesterday));
			param.put("timeEnd", DateUtil.getLastTimeOfDate(yesterday));
		}
		
		List<BonusDayStatsBo> bos = bonusDayStatsDao.searchAllBonusStats(pb, param);
		for (BonusDayStatsBo bo : bos) {
			bo.setSysEmpl( sysEmplService.get(bo.getSysEmplId()) );
		}
		
		return bos;
	}
	
	
	@Override
	public List<BonusDayStatsBo> getDayTotalByUser(User user,Date startTime,Date endTime) {

         return bonusDayStatsDao.getDayTotalByUser(user,startTime,endTime);
         
	}
	
	@Override
	public List<BonusDayStatsBo> getMonthTotalByUser(User user, Date startTime, Date endTime) {
		return bonusDayStatsDao.getMonthTotalByUser(user, startTime, endTime);
	}
	
	
}
