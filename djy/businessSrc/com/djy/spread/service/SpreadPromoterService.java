package com.djy.spread.service;

import java.util.List;
import java.util.Map;

import com.djy.spread.model.SpreadPromoter;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface SpreadPromoterService extends BaseService<SpreadPromoter>{

	SpreadPromoter getByEmplID(String emplID);

	int delSpreadPromoter(Integer[] ids);

	int freezeSpreadPromoter(Integer[] ids);

	int normalSpreadPromoter(Integer[] ids);

	List<SpreadPromoter> search(PagingBean pb, Map<String, Object> param);

}
