package com.djy.co.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface CoPartnerService extends BaseService<CoPartner>{
	
	CoPartner getByUsername(String username);
	
	CoPartner getByMobile(String mobile);
	
	public CoPartner createByUser(User user);

	public List<CoPartner> search(PagingBean pb, Map<String, Object> param);

	public CoPartner getByUserId(Integer userId);

	public List<CoPartner> findByEmplIds(Integer[] emplIds, Integer userType);
	
	public CoPartner getBySpreadCode(String spreadcode);

	public List<CoPartner> searchTreeData(Map<String, Object> param);
	
	public List<CoPartner> getConsByDistance(Integer page,String lat,String lon);
	
	public List<CoPartner> getConsByPage(Integer page);
	
	public List<CoPartner> getAdsByDistance(Integer page,String lat,String lon);

	public int updateCoPartner(Integer[] ids, Integer status);

	public String encodePassword(CoPartner coPartner, String password);

	public CoPartner checkLogin(String account, String password);

}
