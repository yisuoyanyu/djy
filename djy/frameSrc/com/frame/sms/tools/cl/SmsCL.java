package com.frame.sms.tools.cl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SmsCL {
	
	private static final Logger logger = LoggerFactory.getLogger( SmsCL.class );
	
	public static void main(String[] args) {
		sendSms("15359029061", "您好，您的验证码是：12345");
	}
	
	
	public static String sendSms(String mobile, String tplCont, Map<String, Object> data) throws Exception {
		String msg = tplCont;
		
		for (Map.Entry<String, Object> i : data.entrySet()) {
			msg = msg.replaceAll(i.getKey(), i.getValue() + "");
		}
		sendSms(mobile, msg);
		
		return msg;
	}
	
	
	public static void sendSms(String mobile, String msg) {
		try {
			String returnString = SmsCL.batchSend(CLParameter.url, CLParameter.account, CLParameter.pswd, mobile, msg, "1", null);
			System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【短信发送异常】接口：上海创蓝，目标手机号：" + mobile + "，发送内容：" + msg);
			// TODO 处理异常
		}
	}

	/**
	 * 批量发送
	 * 
	 * @param url
	 *            应用地址
	 * @param account
	 *            账号
	 * @param pswd
	 *            密码
	 * @param mobile
	 *            手机号码，多个号码使用","分割
	 * @param msg
	 *            短信内容
	 * @param needstatus
	 *            是否需要状态报告，需要true，不需要false
	 * @param extno
	 *            扩展码
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	private static String batchSend(String url, String account, String pswd, String mobile, String msg,
			String rd, String extno) throws Exception {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
		PostMethod method = new PostMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "", false));
			method.setRequestBody(new NameValuePair[] {
					new NameValuePair("un", account),
					new NameValuePair("pw", pswd),
					new NameValuePair("phone", mobile),
					new NameValuePair("msg", msg),
					new NameValuePair("rd", rd),
					new NameValuePair("extno", extno) });
			HttpMethodParams params = new HttpMethodParams();
			params.setContentCharset("UTF-8");
			method.setParams(params);
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				in.close();// 1
				baos.close();// 2
				String baosString = baos.toString();
				baosString = baosString.replaceAll("%(?![0-9a-fA-F]{2})", "%25");  
				return URLDecoder.decode(baosString, "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
	}
}
