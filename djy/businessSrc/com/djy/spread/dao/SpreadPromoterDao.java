package com.djy.spread.dao;

import java.util.List;
import java.util.Map;

import com.djy.spread.model.SpreadPromoter;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface SpreadPromoterDao extends BaseDao<SpreadPromoter>{

	List<SpreadPromoter> search(PagingBean pb, Map<String, Object> param);

	int updateSpreadPromoterStatus(Integer[] ids, int status);

}
