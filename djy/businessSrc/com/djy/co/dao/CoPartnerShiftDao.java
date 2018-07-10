package com.djy.co.dao;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerShift;
import com.frame.base.dao.BaseDao;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerShiftDao extends BaseDao<CoPartnerShift>{

	List<CoPartnerShift> search(PagingBean pb, Map<String, Object> param);

	String getLastCoPartnerShiftHandoverTime(Map<String, Object> param);

}
