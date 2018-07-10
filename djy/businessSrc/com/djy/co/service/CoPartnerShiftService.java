package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerShift;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerShiftService extends BaseService<CoPartnerShift>{

	List<CoPartnerShift> search(PagingBean pb, Map<String, Object> param);
	
	/**
	 * 获取合作商家最近一次交接班时间
	 * @param param
	 * @return
	 */
	String getLastCoPartnerShiftHandoverTime(Map<String, Object> param);

}
