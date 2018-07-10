package com.frame.wechat.api.utils;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import com.frame.wechat.api.json.JsApiTicket;
import com.frame.wechat.api.json.Sign;
import com.frame.wechat.config.WechatParameter;

public class SignUtil {

	public static Map<String, Sign> signMap = new HashMap<String, Sign>();
	
	
	public static Sign getSign(HttpServletRequest request) {
		
		String url = WechatParameter.serverUrl + request.getServletPath().substring(1);
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString();
		}
		
		Sign sign = signMap.get(url);
		if (sign==null) {
			signUrl(url);
		} else {
			JsApiTicket curTicket = JsApiTicketUtil.queryJsApiTicket();
			JsApiTicket signTicket = sign.getJsApiTicket();
			if ( curTicket.getCreateTime() != signTicket.getCreateTime() ) {
				signUrl(url);
			}
		}
		return signMap.get(url);
	}
	
	
	
	private static void signUrl(String url){
		Sign signCache = signMap.get(url);
		
		Sign sign;
		
		if (signCache == null) {
			sign = new Sign();
		} else {
			sign = signCache;
		}
		
		sign.setJsApiTicket(JsApiTicketUtil.queryJsApiTicket());
		sign.setNoncestr(UUID.randomUUID().toString());
		sign.setTimestamp(System.currentTimeMillis() / 1000);
		sign.setUrl(url);
		
		String signature = "";
		
		String string1 = "jsapi_ticket=" + sign.getJsApiTicket().getTicket() +
                "&noncestr=" + sign.getNoncestr() +
                "&timestamp=" + Long.toString(sign.getTimestamp()) +
                "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		sign.setSignature(signature);
		
		if (signCache == null) {
			signMap.put(url, sign);
		}
	}
	
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
}
