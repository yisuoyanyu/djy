package com.djy.bonus.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.user.model.User;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface BonusDayStatsDao extends BaseDao<BonusDayStatsBo> {
	
	List<BonusDayStatsBo> searchBonusStats(PagingBean pb, Map<String, Object> param);
	
	List<BonusDayStatsBo> searchAllBonusStats(PagingBean pb, Map<String, Object> param);
	
	List<BonusDayStatsBo> getDayTotalByUser(User user,Date startTime,Date endTime);
	
	List<BonusDayStatsBo> getMonthTotalByUser(User user,Date startTime,Date endTime);
	
}
