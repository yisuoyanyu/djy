package com.djy.bonus.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface BonusDayStatsService extends BaseService<BonusDayStatsBo> {
	
	/**
	 * 职员后台获取所有提成统计
	 * @param pb
	 * @param param
	 * @return
	 */
	List<BonusDayStatsBo> searchBonusStats(PagingBean pb, Map<String, Object> param);
	
	/**
	 * 管理后台获取所有提成统计
	 * @param pb
	 * @param param
	 * @return
	 */
	List<BonusDayStatsBo> searchAllBonusStats(PagingBean pb, Map<String, Object> param);
	
	List<BonusDayStatsBo> getDayTotalByUser(User user,Date startTime,Date endTime);
	
	List<BonusDayStatsBo> getMonthTotalByUser(User user,Date startTime,Date endTime);
	
}
