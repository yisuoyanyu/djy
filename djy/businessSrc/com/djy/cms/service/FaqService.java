package com.djy.cms.service;

import java.util.List;

import com.djy.cms.model.Faq;
import com.frame.base.service.BaseService;

public interface FaqService extends BaseService<Faq>{

	List<Faq> getAllFaqBySort();
}
