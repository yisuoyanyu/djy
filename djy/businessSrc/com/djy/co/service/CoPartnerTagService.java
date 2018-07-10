package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerTag;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerTagService extends BaseService<CoPartnerTag>{

	int delCoPartnerTag(Integer[] ids);

	List<CoPartnerTag> search(PagingBean pb, Map<String, Object> param);

}
