package com.djy.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.model.CoPartner;
import com.djy.user.dao.UserDao;
import com.djy.user.enumtype.UserStatus;
import com.djy.user.enumtype.UserType;
import com.djy.user.model.User;
import com.djy.user.service.UserAccountService;
import com.djy.user.service.UserService;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatUserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserDao userDao;

	@Override
	public void createByWechatUser(WechatUser wechatUser, String sceneStr) {

		User sponsor = null; // 推荐人
		CoPartner ofCoPartner = null; // 所属商家

		if (!StringUtil.isBlank(sceneStr)) {// 扫描二维码进来的
			if (sceneStr.startsWith("U")) {
				sponsor = this.getUserBySpreadCode(sceneStr);

				// 判断Sponsor是否为null;
				if (sponsor != null) {
					if (sponsor.getOfCoPartner() != null) {
						ofCoPartner = sponsor.getOfCoPartner();
					} else {
						if (sponsor.getCoPartner() != null) {
							ofCoPartner = sponsor.getCoPartner();
						}
					}
					
					userAccountService.addUserCanReceiveCoupons(sponsor);
					
				}
			}
		}

		User user = new User();
		String userName = "wx_" + wechatUser.getOpenid(); // 唯一用户名
		user.setUsername(userName);
		user.setInsertTime(new Date());
		user.setStatus(UserStatus.normal.getId());
		user.setType(UserType.customer.getId());
		user.setSponsor(sponsor);
		user.setOfCoPartner(ofCoPartner);
		this.save(user);

		user.setSpreadCode("U" + String.format("%d", user.getId() + 100600));
		this.update(user);

		userAccountService.addUserAccount(user);

		wechatUser.setUser(this.get(user.getId()));
		wechatUserService.saveOrUpdate(wechatUser);

	}

	@Override
	public User getUserByMobile(String mobile) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("mobile", mobile);
		return this.getByFilter(hqlFilter);
	}

	@Override
	public User getUserBySpreadCode(String spreadCode) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("spreadCode", spreadCode);
		return this.getByFilter(hqlFilter);
	}

	@Override
	public List<User> search(PagingBean pb, Map<String, Object> param) {
		return userDao.search(pb, param);
	}

	@Override
	public long getTotalRefNum(User user) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("sponsor.id", user.getId(), SqlOperator.equal);
		return this.countByFilter(filter);
	}

	@Override
	public List<User> findReferralByUser(User user) {
		HqlFilter filter = new HqlFilter();
		filter.addFilterFirstOr("sponsor.id", user.getId(), SqlOperator.equal);
		filter.addFilterEndOr("sponsor.sponsor.id", user.getId(), SqlOperator.equal);
		return this.findByFilter(filter);
	}

	@Override
	public List<User> findReferPageByUser(PagingBean pb, User user) {
		return this.userDao.findReferPageByUser(pb, user);
	}

	@Override
	public List<User> findMyspreadUser(User user, Integer page) {
		return this.userDao.findMyspreadUser(user, page);
	}

	@Override
	public User getUserByUrl(String url) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("qrcodeUrlStr", url);
		hqlFilter.addFilter("status", UserStatus.normal.getId());
		return getByFilter(hqlFilter);
	}

	@Override
	public Long getUserNumByDate(Date startDate, Date endDate) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("insertTime", startDate, SqlOperator.greaterEqual);
		hqlFilter.addFilter("insertTime", endDate, SqlOperator.lessThen);
		return countByFilter(hqlFilter);
	}

	@Override
	public Integer getUserNumAll() {
		return count().intValue();
	}

	@Override
	public Long getFirstConsumeUserNum(Map<String, Object> param) {
		return userDao.getFirstConsumeUserNum(param);
	}

	@Override
	public Long getUserNum(Map<String, Object> param) {
		HqlFilter hqlFilter = new HqlFilter();

		Date startTime = (Date) param.get("timeStart");
		if (!StringUtil.isEmpty(startTime)) {
			hqlFilter.addFilter("insertTime", startTime, SqlOperator.greaterEqual);
		}

		Date endTime = (Date) param.get("timeEnd");
		if (!StringUtil.isEmpty(endTime)) {
			hqlFilter.addFilter("insertTime", endTime, SqlOperator.lessThen);
		}

		return countByFilter(hqlFilter);
	}

	@Override
	public Long getFirstConsumeUserNumByCoParnerId(Map<String, Object> param) {
		return userDao.getFirstConsumeUserNumByCoParnerId(param);
	}

}
