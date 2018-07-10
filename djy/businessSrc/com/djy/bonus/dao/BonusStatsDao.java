package com.djy.bonus.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.bonus.model.BonusStats;
import com.djy.user.model.User;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface BonusStatsDao extends BaseDao<BonusStats>{
	
	double getTotalCstmConsumeByUser(User user);
	
	double getTotalBonusByUser(User user);
	
	List<BonusStats> getDayAccList(User user, Date getDate);

	List<BonusStats> search(PagingBean pb, Map<String, Object> param);
	
}
