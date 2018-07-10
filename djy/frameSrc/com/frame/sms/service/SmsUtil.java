package com.frame.sms.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.base.utils.StringUtil;
import com.frame.sms.json.SmsTpl;
import com.frame.sms.tools.cl.SmsCL;
import com.frame.sms.tools.jh.SmsJH;

public class SmsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger( SmsUtil.class );
	
	
	public static void main(String[] args){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", "123456");
		SmsTpl smsTpl = new SmsTpl();
		smsTpl.setJhTplId( "10932" );
		smsTpl.setClTplCont( "【油惠站】亲爱的用户，您本次操作的验证码为#code#。有效期为30分钟，请尽快验证！" );
		sendSms("13691015791", smsTpl, params);
	}
	
	
	/**
	 * 发送短信
	 * @param mobile 目标手机号
	 * @param tpl 短信模板
	 * @param params 传入的参数，用于替换模板关键词
	 * @return 发送的短信内容
	 */
	public static String sendSms(String mobile, SmsTpl tpl, Map<String, Object> params) {
		
		if ( tpl == null ) {
			return null;
		}
		
		if ( StringUtil.isEmpty( tpl.getClTplCont() ) && StringUtil.isEmpty( tpl.getJhTplId() ) ) {
			return null;
		}
		
		Map<String,Object> data = new HashMap<>();
		if ( params != null && !params.isEmpty() ) {
			for ( String key : params.keySet() ) {
				data.put("#" + key + "#", params.get(key));
			}
		}
		
		if ( ! StringUtil.isEmpty( tpl.getClTplCont() ) ) {
			try {
				String msg = SmsCL.sendSms(mobile, tpl.getClTplCont(), data);
				return msg;
			} catch(Exception e_CL) {
				//logger.error("【创蓝】发送短信异常，目标手机：" + mobile + "，错误：" + e_CL.getMessage());
				System.out.println("【创蓝】发送短信异常，目标手机：" + mobile + "，错误：" + e_CL.getMessage());
				if ( ! StringUtil.isEmpty( tpl.getJhTplId() ) ) {
					try {
						SmsJH.sendSms(mobile, tpl.getJhTplId(), data);
						return "【聚合（id：" + tpl.getJhTplId() + "）】" + data.toString();
					} catch(Exception e_JH) {
						// logger.error("【聚合】发送短信异常，目标手机：" + mobile +"，错误：" + e_JH.getMessage());
						System.out.println("【聚合】发送短信异常，目标手机：" + mobile +"，错误：" + e_JH.getMessage());
						return null;
					}
				}
			}
		} else if ( ! StringUtil.isEmpty( tpl.getJhTplId() ) ) {
			try {
				SmsJH.sendSms(mobile, tpl.getJhTplId(), data);
				return "【聚合（id：" + tpl.getJhTplId() + "）】" + data.toString();
			} catch(Exception e_JH) {
				// logger.error("【聚合】发送短信异常，目标手机：" + mobile +"，错误：" + e_JH.getMessage());
				System.out.println("【聚合】发送短信异常，目标手机：" + mobile +"，错误：" + e_JH.getMessage());
				return null;
			}
		}
		
		return null;
	}
	
	
	/**
	 * 发送短信。短信内容模板向提供商报备后到达比较高，因此建议尽量用短信模板发送。
	 * @param mobile 目标手机号
	 * @param msg 短信内容
	 */
	public static void sendSms(String mobile, String msg){
		try {
			SmsCL.sendSms(mobile, msg);
		} catch(Exception e_CL) {
			logger.error("【创蓝】发送短信异常，目标手机：" + mobile + "，错误：" + e_CL.getMessage());
		}
	}
	
}
