package com.frame.sms.tools.jh;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meidusa.fastjson.JSONObject;


public class SmsJH {
	
	private static final Logger logger = LoggerFactory.getLogger( SmsJH.class );
	
	public static void main(String[] args) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("#code#", "123456");
			sendSms("15359029061", "10932", params);
		} catch (Exception e) {
			
		}
	}
	
	
	/**
	 * 发送短信
	 * @param mobile 目标手机号
	 * @param tplId 模板id
	 * @param data 模板变量值
	 * @throws Exception
	 */
	public static void sendSms(String mobile, String tplId, Map<String,Object> data) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("tpl_id", tplId);
		params.put("tpl_value", urlencode(data));
		params.put("key", JHParameter.appKey);
		String result = HttpClientUtil.getHttp(JHParameter.url, params);
		JSONObject object = JSONObject.parseObject(result);
		if (object.getInteger("error_code") == 0) {
			logger.debug("短信发送result:" + object.get("result"));
		} else {
			logger.error(result);
			System.out.println(result);
			throw new Exception("短信发送失败请重试");
		}
	}
	
	
	/**
	 * 将map型转为请求参数型
	 * @param data
	 * @return
	 */
	private static String urlencode(Map<String,Object>data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String,Object> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
}
