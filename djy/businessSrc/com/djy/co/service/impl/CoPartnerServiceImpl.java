package com.djy.co.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerDao;
import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAccount;
import com.djy.co.service.CoPartnerAccountService;
import com.djy.co.service.CoPartnerService;
import com.djy.user.enumtype.UserType;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;


@Service("coPartnerService")
public class CoPartnerServiceImpl extends BaseServiceImpl<CoPartner> implements CoPartnerService{
	
	@Autowired
	private CoPartnerDao coPartnerDao;
	
	@Autowired
	private CoPartnerAccountService coPartnerAccountService;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public CoPartner createByUser(User user) {
		
		CoPartner coPartner = new CoPartner();
		coPartner.setStatus(CoPartnerStatus.follow.getId());//首次设置为“关注”状态
		coPartner.setUser(user);
		coPartner.setInsertTime(new Date());
		this.save(coPartner);
		
		CoPartnerAccount account = new CoPartnerAccount();
		account.setCoPartner( coPartner );
		coPartnerAccountService.save( account );
		
		return coPartner;
	}
	
	
	@Override
	public CoPartner getBySpreadCode(String spreadcode) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("spreadCode", spreadcode);
		return this.getByFilter(hqlFilter);
	}
	
	
	@Override
	public List<CoPartner> search(PagingBean pb, Map<String, Object> param) {
		
		return coPartnerDao.search(pb,param);
		
	}
	
	
	@Override
	public CoPartner getByUserId(Integer userId) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("user_ID", userId);
		return this.getByFilter(filter);
	}
	
	
	@Override
	public List<CoPartner> findByEmplIds(Integer[] emplIds, Integer userType) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("sys_empl_ID", emplIds, SqlOperator.in);
		filter.addFilter("user.type", userType);
		if ( userType == UserType.customer.getId() ) {
			filter.addFilter("user.sponsor", null);
		}
		return this.findByFilter(filter);
	}
	
	
	@Override
	public List<CoPartner> searchTreeData(Map<String, Object> param) {
		
		HqlFilter filter = new HqlFilter();
		
		filter.addFilter("status", CoPartnerStatus.normal.getId());
		
		return this.findByFilter(filter);
	}
	
	
	@Override
	public List<CoPartner> getConsByDistance(Integer page,String lat, String lon) {
		
		return coPartnerDao.getConsByDistance(page,lat, lon);
		
	}
	
	@Override
	public List<CoPartner> getConsByPage(Integer page) {

		return coPartnerDao.getConsByPage(page);
	}


	@Override
	public List<CoPartner> getAdsByDistance(Integer page, String lat, String lon) {
		
		return coPartnerDao.getAdsByDistance(page,lat, lon);
		
	}
	
	
	@Override
	public int updateCoPartner(Integer[] ids, Integer status) {
		
		return this.coPartnerDao.updateCoPartnerStatus(ids, status);
		
	}


	@Override
	public String encodePassword(CoPartner coPartner, String password) {
		password = (new StringBuilder(String.valueOf(password))).append(coPartner.getId()).toString();
		for (int i = 0; i < 128 + coPartner.getId().intValue() % 128; i++)
			password = StringUtil.md5s(password);

		return password;
	}


	@Override
	public CoPartner checkLogin(String account, String password) {
		
		User user = userService.getUserByMobile(account);
		
		if (user != null && user.getCoPartner().getStatus() != CoPartnerStatus.freeze.getId()) {
			CoPartner coPartner = this.getByUserId(user.getId());
			if (coPartner == null)
				return null;
			
			if ( coPartner.getPassword().equals(encodePassword(coPartner, password)) )
				return coPartner;
			else
				return null;
		}
		return null;
		
	}


	@Override
	public CoPartner getByUsername(String username) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("username", username);
		return this.getByFilter(filter);
	}


	@Override
	public CoPartner getByMobile(String mobile) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("coPartner.user.mobile", mobile);
		return this.getByFilter(filter);
	}

	
}
