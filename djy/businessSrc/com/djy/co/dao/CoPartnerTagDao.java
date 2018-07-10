package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerTag;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerTagDao extends BaseDao<CoPartnerTag>{

	List<CoPartnerTag> search(PagingBean pb, Map<String, Object> param);

}
