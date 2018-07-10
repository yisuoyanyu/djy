package com.djy.sms.service;

import com.djy.sms.model.SmsCode;
import com.frame.base.service.BaseService;

public interface SmsCodeService extends BaseService<SmsCode> {


	/**
	 * 发送短信验证码
	 * @param scene
	 *   业务场景。1001—用户注册，1002—绑定手机。  
	 * @param mobile
	 *   手机号
	 * @param clientIP
	 *   客户端IP。为null或""时，则不验证。
	 * @return
	 *   token令牌
	 * @throws Exception
	 */
	public String sendSmsCode(Integer scene, String mobile, String clientIP) throws Exception;

	/**
	 * 判断短信验证码，错误则抛出异常
	 * @param scene
	 *   业务场景。1001—用户注册，1002—绑定手机。  
	 * @param mobile
	 *   手机号
	 * @param token
	 *   token令牌
	 * @param code
	 *   短信验证码
	 * @param clientIP
	 *   客户端IP
	 * @throws Exception
	 */
	public void validateSmsCode(Integer scene, String mobile, String token, String code, String clientIP) throws Exception;
}
