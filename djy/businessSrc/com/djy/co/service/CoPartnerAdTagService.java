package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartnerAd;
import com.djy.co.model.CoPartnerAdTag;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerAdTagService extends BaseService<CoPartnerAdTag>{

	int delCoPartnerAdTag(Integer[] ids);

	List<CoPartnerAdTag> search(PagingBean pb, Map<String, Object> param);

	void delByCoPartnerAd(CoPartnerAd coPartnerAd);

}
