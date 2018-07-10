package com.djy.cms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.djy.cms.model.Faq;
import com.djy.cms.service.FaqService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("faqService")
public class FaqServiceImpl extends BaseServiceImpl<Faq> implements FaqService{

	@Override
	public List<Faq> getAllFaqBySort() {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addOrder("sortNumber", false);
		return findByFilter(hqlFilter);
	}

}
