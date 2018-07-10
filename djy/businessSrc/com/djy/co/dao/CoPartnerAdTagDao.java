package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerAdTag;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerAdTagDao extends BaseDao<CoPartnerAdTag>{

	List<CoPartnerAdTag> search(PagingBean pb, Map<String, Object> param);

}
