package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerAd;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerAdDao extends BaseDao<CoPartnerAd>{

	int updateCoPartnerAdStatus(Integer[] ids, Integer status);

	List<CoPartnerAd> search(PagingBean pb, Map<String, Object> param);

}
