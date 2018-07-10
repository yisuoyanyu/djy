package com.djy.wechat.service.impl;

import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.wechat.api.json.UserInfo;
import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.service.CoPartnerService;
import com.djy.user.enumtype.UserStatus;
import com.djy.user.enumtype.UserType;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatUserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Administrator
 *
 */
@Service("wechatUserService")
public class WechatUserServiceImpl extends BaseServiceImpl<WechatUser> implements WechatUserService {
	
	
	@Autowired
	private UserService userService;
	@Autowired
	private CoPartnerService coPartnerService;
	
	
	private WechatUser transferByUserInfo(WechatUser wechatUser, UserInfo userInfo) {
		wechatUser.setOpenid( userInfo.getOpenid() );
		wechatUser.setSubscribe( 1 );
		wechatUser.setNickname( StringUtil.filterEmoji( userInfo.getNickname() ) );
		wechatUser.setSex( userInfo.getSex() != null ? Integer.valueOf(userInfo.getSex()) : 0 );
		wechatUser.setProvince( StringUtil.filterEmoji( userInfo.getProvince() ) );
		wechatUser.setCity( StringUtil.filterEmoji( userInfo.getCity() ) );
		wechatUser.setCountry( StringUtil.filterEmoji( userInfo.getCountry() ) );
		wechatUser.setAddress( StringUtil.filterEmoji( userInfo.getCountry() + userInfo.getProvince() + userInfo.getCity() ) );
		wechatUser.setHeadimgUrl( userInfo.getHeadimgurl() );
		wechatUser.setSubscribeTime( Long.parseLong(userInfo.getSubscribe_time()) );
		wechatUser.setRemark( StringUtil.filterEmoji( userInfo.getRemark() ) );
		wechatUser.setGroupID( userInfo.getGroupid() );
		return wechatUser;
	}
	
	
	@Override
	public WechatUser refreshByUserInfo(WechatUser wechatUser, UserInfo userInfo, String sceneStr) {
		
		wechatUser = transferByUserInfo(wechatUser, userInfo);
		
		User user = wechatUser.getUser();
		user.setStatus(UserStatus.normal.getId());
		userService.update(user);
		
		wechatUser = user.getWechatUser();
		wechatUser.setSubscribe(1);
		this.update(wechatUser);
		
		if (!StringUtil.isBlank(sceneStr) && sceneStr.startsWith("COPARTNER") ) {//总部发展合作商家
			if (user.getType() != UserType.copartner.getId()) {//之前为普通用户
				user.setType(UserType.copartner.getId());
				if (user.getCoPartner() == null)
					coPartnerService.createByUser( user );	//增加新的合作商家
			} else {
				CoPartner coPartner = user.getCoPartner();//表示其之前就是一个合作商家
				coPartner.setStatus(CoPartnerStatus.normal.getId());//设置其状态为可用状态
				coPartnerService.update(coPartner);
			}
		} else {
			if (user.getType() == UserType.copartner.getId()) {//表示之前是合作商家，现在变成普通用户
				user.setType(UserType.customer.getId());//其类型修改为普通用户
			}
		}
		userService.update(user);
		return wechatUser;
	}
	
	
	@Override
	public WechatUser createByUserInfo(UserInfo userInfo, String sceneStr) {
		
		// 用户是之前就关注了该公众号，数据库中没有数据，此步骤保存用户数据
		WechatUser wechatUser = new WechatUser();
		wechatUser = transferByUserInfo(wechatUser, userInfo);
		wechatUser.setInsertTime(new Date());
		this.save(wechatUser);
		
		userService.createByWechatUser(wechatUser, sceneStr);
		
		return wechatUser;
	}
	
	
	/**
	 * 判断用户是否已经绑定了平台账户
	 * 
	 * @param wechatUser
	 * @return
	 */
	@Override
	public Boolean isBind(WechatUser wechatUser) {
		if (wechatUser == null)
			return false;

		if (wechatUser.getUser() == null)
			return false;

		if (wechatUser.getSubscribe() != 1)
			return false;

		return true;
	}

	/**
	 * 根据OpenID获取用户信息
	 * 
	 * @param OpenID
	 * @return
	 */
	@Override
	public WechatUser getByOpenID(String OpenID) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("openID", OpenID);
		WechatUser wechatUser = getByFilter(hqlFilter);
		return wechatUser;
	}

	@Override
	public WechatUser getByUserId(Integer userId) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user.id", userId);
		WechatUser wechatUser = getByFilter(hqlFilter);
		return wechatUser;
	}

	@Override
	public Long getUserNumToday(Date startDate, Date endDate) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("insertTime", startDate,SqlOperator.greaterEqual);
		hqlFilter.addFilter("insertTime",endDate,SqlOperator.lessThen);
		return countByFilter(hqlFilter);
	}

	@Override
	public Integer getUserNumAll() {
		return count().intValue();
	}
	
}
