package com.djy.job.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.djy.bonus.service.BonusStatsService;
import com.djy.job.service.BonusStatsQuartzService;
import com.frame.base.utils.DateUtil;

@Service("bonusStatsQuartzService")
public class BonusStatsQuartzServiceImpl implements BonusStatsQuartzService {
	
	private static Logger logger = LoggerFactory.getLogger(BonusStatsQuartzServiceImpl.class);
	
	@Autowired
	private BonusStatsService bonusStatsService;
	
	@Override
	@Transactional
	public void bonusStatsQuartz() {
		logger.info("开始 生成业绩提成排程……");
		try {
			
			Date date = DateUtil.addDay(DateUtil.getFirstTimeOfDate(new Date()), -1);
			
			bonusStatsService.createBonusStats(date);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
		}
		logger.info("完成 生成业绩提成排程！");
	}
	
}
