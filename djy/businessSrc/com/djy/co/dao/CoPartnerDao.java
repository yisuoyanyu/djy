package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerDao extends BaseDao<CoPartner>{

	List<CoPartner> search(PagingBean pb, Map<String, Object> param);
	
	List<CoPartner> getConsByDistance(Integer page,String lat,String lon);
	
	List<CoPartner> getConsByPage(Integer page);
	
	List<CoPartner> getAdsByDistance(Integer page,String lat,String lon);

	int updateCoPartnerStatus(Integer[] ids, Integer status);

}
