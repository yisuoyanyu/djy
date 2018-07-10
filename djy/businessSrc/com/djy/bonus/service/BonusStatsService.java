package com.djy.bonus.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.bonus.model.BonusStats;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface BonusStatsService extends BaseService<BonusStats>{
	
	/**
	 * 通过会员获取总营业额
	 * @param user
	 * @return
	 */
	double getTotalCstmConsumeByUser(User user);
	
	/**
	 * 通过会员获取总的提成金额
	 * @param user
	 * @return
	 */
	double getTotalBonusByUser(User user);
	
	List<BonusStats> getDayAccList(User user,Date getDate);
	
	List<BonusStats> search(PagingBean pb, Map<String, Object> param);
	
	void createBonusStats(Date date);
	
}
