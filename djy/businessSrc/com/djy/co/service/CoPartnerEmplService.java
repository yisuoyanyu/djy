package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerEmplService extends BaseService<CoPartnerEmpl>{
	
	List<CoPartnerEmpl> getAvailableEmpl(CoPartner coPartner);

	CoPartnerEmpl getByEmplID(String emplID);

	List<CoPartnerEmpl> search(PagingBean pb, Map<String, Object> param);
	
	int delCoPartnerEmpl(Integer[] ids);
	
	int freezeCoPartnerEmpl(Integer[] ids);

	int normalCoPartnerEmpl(Integer[] ids);


}
