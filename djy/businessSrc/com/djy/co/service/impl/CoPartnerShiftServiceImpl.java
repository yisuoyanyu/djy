package com.djy.co.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerShiftDao;
import com.djy.co.model.CoPartnerShift;
import com.djy.co.service.CoPartnerShiftService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerShiftService")
public class CoPartnerShiftServiceImpl extends BaseServiceImpl<CoPartnerShift> implements CoPartnerShiftService{
	
	@Autowired
	private CoPartnerShiftDao coPartnerShiftDao;
	
	@Override
	public List<CoPartnerShift> search(PagingBean pb, Map<String, Object> param) {
		return coPartnerShiftDao.search(pb,param);
	}

	@Override
	public String getLastCoPartnerShiftHandoverTime(Map<String, Object> param) {
		return coPartnerShiftDao.getLastCoPartnerShiftHandoverTime(param);
	}

}
