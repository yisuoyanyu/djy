package com.djy.sms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.sms.enumtype.SmsCodeScene;
import com.djy.sms.model.SmsCode;
import com.djy.sms.service.SmsCodeService;
import com.djy.sms.service.SmsTmplService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.exception.FrontShowException;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringRandomUtil;
import com.frame.base.utils.StringUtil;
import com.frame.sms.service.SmsUtil;

@Service("smsCodeService")
public class SmsCodeServiceImpl extends BaseServiceImpl<SmsCode> implements SmsCodeService {

	private static Logger logger = LoggerFactory.getLogger( SmsCodeServiceImpl.class );
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SmsTmplService smsTmplService;
	
	
	
	@Override
	public String sendSmsCode(Integer scene, String mobile, String clientIP) throws Exception {
		
		User user = userService.getByFilter("mobile", mobile);
		
		if (scene == SmsCodeScene.userRegister.getId()) {
			if (user != null) {
				throw new FrontShowException("此手机号已注册！");
			}
		} else if (scene == SmsCodeScene.bindingMobile.getId()) {
			if (user != null) {
				throw new FrontShowException("此手机号已绑定！");
			}
		} else {
			throw new FrontShowException("请求短信验证码时，找不到业务类型！");
		}
		
		String token = StringUtil.md5s(new Date().getTime() + StringRandomUtil.randomABC(6));
		String code = StringRandomUtil.randomInt(6);
		
		// 发送手机验证码
		try {
			SmsCode smsCode = new SmsCode();
			smsCode.setScene(scene);
			smsCode.setMobile(mobile);
			smsCode.setClientIP(clientIP);
			smsCode.setToken(token);
			smsCode.setCode(code);
			this.save(smsCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		
		SmsUtil.sendSms(mobile, smsTmplService.getTplByCode( "smsCode" ), params);
		
		return token;
	}
	
	
	@Override
	public void validateSmsCode(Integer scene, String mobile, String token, String code, String clientIP) throws Exception {
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("scene", scene);
		filter.addFilter("mobile", mobile);
		filter.addFilter("token", token);
		filter.addFilter("code", code);
		SmsCode smsCode = this.getByFilter(filter);
		
		if ( smsCode == null ) {
			 throw new FrontShowException("短信验证码错误！");
		}
		
		if ( !StringUtil.isBlank(smsCode.getClientIP()) ) {
			if ( !smsCode.getClientIP().equals(clientIP) ) {
				throw new FrontShowException("短信验证码客户端IP错误！");
			}
		}
		
		Date currentTime = new Date();
		Date smsTime = smsCode.getInsertTime();
		if (DateUtil.addMinute(smsTime, 30).compareTo(currentTime) < 0) {
			throw new FrontShowException("短信验证码超时！");
		}
		
		// 删除对应业务场景下该号码的记录
		HqlFilter delFilter = new HqlFilter();
		delFilter.addFilter("scene", scene);
		delFilter.addFilter("mobile", mobile);
		this.deleteByFilter(delFilter);
	}
	
}
