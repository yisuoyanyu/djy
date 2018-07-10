package com.djy.user.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.djy.user.model.User;
import com.djy.wechat.model.WechatUser;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface UserService extends BaseService<User> {

	public void createByWechatUser(WechatUser wechatUser, String sceneStr);
	
	User getUserByMobile(String mobile);
	
	User getUserBySpreadCode(String spreadCode);

	public List<User> search(PagingBean pb, Map<String, Object> param);
	
	long getTotalRefNum(User user);
	
	List<User> findReferralByUser(User user);
	
	List<User> findReferPageByUser(PagingBean pb,User user);
	
	List<User> findMyspreadUser(User user,Integer page);
	
	User getUserByUrl(String url);

	public Long getUserNumByDate(Date startDate, Date endDate);

	public Integer getUserNumAll();
	
	/**
	 * 被邀会员数量
	 * @param param
	 * @return
	 */
	public Long getFirstConsumeUserNum(Map<String, Object> param);
	
	/**
	 * 获取平台会员数量
	 * @param param
	 * @return
	 */
	public Long getUserNum(Map<String, Object> param);
	
	/**
	 * 被邀会员数量
	 * @param todayParam
	 * @return
	 */
	public Long getFirstConsumeUserNumByCoParnerId(Map<String, Object> todayParam);
}
