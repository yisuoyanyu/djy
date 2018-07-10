package com.djy.spread.dao;

import java.util.List;
import java.util.Map;

import java.util.List;

import com.djy.spread.model.SpreadCommission;
import com.djy.user.model.User;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface SpreadCommissionDao extends BaseDao<SpreadCommission>{

	List<SpreadCommission> search(PagingBean pb, Map<String, Object> param);

	double countCommissionTotalAmount(Map<String, Object> param);

	List<SpreadCommission> getCommByUser(User user, Integer page);
	
}
