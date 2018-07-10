package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerEmpl;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerEmplDao extends BaseDao<CoPartnerEmpl>{

	List<CoPartnerEmpl> search(PagingBean pb, Map<String, Object> param);
	
	int updateCoPartnerEmplStatus(Integer[] ids, int status);

}
