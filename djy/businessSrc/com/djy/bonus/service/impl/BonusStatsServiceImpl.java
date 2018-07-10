package com.djy.bonus.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.bonus.dao.BonusStatsDao;
import com.djy.bonus.model.BonusStats;
import com.djy.bonus.service.BonusStatsService;
import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.service.CoPartnerService;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.sys.model.SysEmpl;
import com.djy.user.model.User;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.vo.PagingBean;


@Service("bonusStatsService")
public class BonusStatsServiceImpl extends BaseServiceImpl<BonusStats> implements BonusStatsService {
	
	private static Logger logger = LoggerFactory.getLogger(BonusStatsServiceImpl.class);
	
	@Autowired
	private BonusStatsDao bonusStatsDao;
		
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	
	
	@Override
	public double getTotalCstmConsumeByUser(User user) {
		return bonusStatsDao.getTotalCstmConsumeByUser(user);
	}
	
	
	@Override
	public double getTotalBonusByUser(User user) {
		
		return bonusStatsDao.getTotalBonusByUser(user);
	}

	
	@Override
	public List<BonusStats> getDayAccList(User user, Date getDate) {
		
		Date startDate = DateUtil.getFirstTimeOfDate(getDate);
		Date endDate = DateUtil.getLastTimeOfDate(getDate);

		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("sysEmpl.id", user.getSysEmpl().getId());
		hqlFilter.addFilter("insertTime", startDate,SqlOperator.greaterEqual);
		hqlFilter.addFilter("insertTime", endDate,SqlOperator.lessEqual);
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
		
	}

	
	@Override
	public List<BonusStats> search(PagingBean pb, Map<String, Object> param) {
		return bonusStatsDao.search(pb,param);
	}

	
	@Override
	public void createBonusStats(Date date) {
		
		int page = 1;
		int rows = 100;
		
		List<CoPartner> coPartners;
		
		do {
			HqlFilter hqlFilter = new HqlFilter();
			//hqlFilter.addFilter("coMode", CoPartnerMode.sysDeposit.getId());
			hqlFilter.addFilter("sysEmpl", null, SqlOperator.notEqual);
			coPartners = coPartnerService.findByFilter(hqlFilter, page, rows);
			
			for (int i=0; i<coPartners.size(); i++ ) {
				CoPartner coPartner = coPartners.get(i);
				try {
					createBonusStats(coPartner, date);
				} catch (Exception e) {
					logger.error("生成业绩统计时发生错误！参数：" 
							+ "coPartnerId=" + coPartner.getId() 
							+ " date=" + date.toString() 
							+ " 错误信息：" + e.getMessage(), 
							e);
				}
			}
			
		    if ( coPartners.size() < rows )
		        break;
			
		    page++;
		    
		} while (coPartners.size() > 0);
		
	}
	
	
	private BonusStats createBonusStats(CoPartner coPartner, Date date) {
		
		/*
		// 非“平台预存”模式，不计算提成。
		if ( coPartner.getCoMode() != CoPartnerMode.sysDeposit.getId() ) {
			return null;
		}
		*/
		
		SysEmpl sysEmpl = coPartner.getSysEmpl();
		if (coPartner.getSysEmpl() == null) {
			return null;
		}
		
		Date statsDate = DateUtil.toDate(DateUtil.formatDate(date, "yyyy-MM-dd"));
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("coPartner.id", coPartner.getId());
		filter.addFilter("statsDate", statsDate);
		BonusStats oldBonusStats = this.getByFilter(filter);
		
		if (oldBonusStats != null)
			return oldBonusStats;
		
		Date startTime = DateUtil.getFirstTimeOfDate(date);
		Date endTime = DateUtil.getLastTimeOfDate(date);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("coPartnerId", coPartner.getId());
		param.put("timeStart", startTime);
		param.put("timeEnd", endTime);
		
		// 会员消费总额
		double totalCstmConsume = consumeOrderService.getConsumeOrderAmount(param);
		
		// 提成金额
		double bonus = 0;
		
		if ( coPartner.getCoMode() == CoPartnerMode.perOrder.getId() ) {
			
			// 提成金额 = 会员消费总额 * 提成比例
			bonus = totalCstmConsume * sysEmpl.getBonusPercent() / 100;
			
		} else if ( coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId() ) {
			
			// 指标金额 = 平台最高预存限额 * 指标比例
			double indexAmount = coPartner.getMaxSysDeposit() * sysEmpl.getIndexPercent() / 100;
			
			if (totalCstmConsume >= indexAmount) {
				
				// 提成金额 = 会员消费总额 * 提成比例
				bonus = totalCstmConsume * sysEmpl.getBonusPercent() / 100;
				
			}
			
		}
		
		BonusStats bonusStats = new BonusStats();
		bonusStats.setCoPartner( coPartner );
		bonusStats.setStatsDate( statsDate );
		bonusStats.setTotalCstmConsume( totalCstmConsume );
		if ( coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId() ) {
			bonusStats.setMaxSysDeposit( coPartner.getMaxSysDeposit() );
			bonusStats.setIndexPercent( sysEmpl.getIndexPercent() );
		}
		bonusStats.setBonusPercent( sysEmpl.getBonusPercent() );
		bonusStats.setBonus( bonus );
		bonusStats.setSysEmpl( sysEmpl );
		bonusStats.setInsertTime( new Date() );
		this.save( bonusStats );
		
		return bonusStats;
		
	}
	
	
}
