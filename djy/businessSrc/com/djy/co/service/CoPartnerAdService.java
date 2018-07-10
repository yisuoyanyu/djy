package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerAdService extends BaseService<CoPartnerAd>{

	int delCoPartnerAd(Integer[] ids);

	int updateCoPartnerAd(Integer[] ids, Integer status);

	List<CoPartnerAd> search(PagingBean pb, Map<String, Object> param);

	List<CoPartnerAd> getByCopartner(CoPartner coPartner);
}
