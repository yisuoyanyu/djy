package com.djy.co.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartnerApply;
import com.djy.co.service.CoPartnerApplyService;
import com.djy.user.model.User;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("coPartnerApplyService")
public class CoPartnerApplyServiceImpl extends BaseServiceImpl<CoPartnerApply> implements CoPartnerApplyService{

	@Override
	public List<CoPartnerApply> findByUser(User user) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
	}

	
}
