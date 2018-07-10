package com.frame.sms.tools.jh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String postHttp(String serviceQeqURL, String serviceReqParams2Encode) {
		String responseMsg = "";

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("UTF-8");

		// 构造PostMethod的实例
		PostMethod postMethod = new PostMethod(serviceQeqURL);

		NameValuePair[] data = { new NameValuePair("param", serviceReqParams2Encode) };
		postMethod.setRequestBody(data);

		try {

			// 执行postMethod,调用远程HTTP Service
			int statusCode = httpClient.executeMethod(postMethod);// 200

			if (statusCode != HttpStatus.SC_OK) {
				responseMsg = "-999";
			} else {
				responseMsg = postMethod.getResponseBodyAsString().trim();
			}

		} catch (HttpException e) {
			responseMsg = "-998";
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			responseMsg = "-997";
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseMsg = "-996";
			logger.error(e.getMessage(), e);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	public static String getHttp(String url, Map<String, String> params) {
		String responseMsg = "";

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("UTF-8");

		if (url.indexOf('?') == -1) {// not found
			url += '?';
		}

		GetMethod getMethod = null;
		try {
			// 构造GetMethod的实例
			getMethod = new GetMethod(url + toQueryString(params));
			System.out.println(url + toQueryString(params));
			// 执行postMethod,调用远程HTTP Service
			int statusCode = httpClient.executeMethod(getMethod);// 200

			if (statusCode != HttpStatus.SC_OK) {
				responseMsg = "-999";
			} else {
				responseMsg = getMethod.getResponseBodyAsString().trim();
			}

		} catch (HttpException e) {
			responseMsg = "-998";
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			responseMsg = "-997";
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseMsg = "-996";
			logger.error(e.getMessage(), e);
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	public static String postHttp(String url, Map<String, String> params) {
		String responseMsg = "";

		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("UTF-8");

		// 构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);

		List<NameValuePair> data = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> param : params.entrySet()) {
			data.add(new NameValuePair(param.getKey(), param.getValue()));
		}
		postMethod.setRequestBody(data.toArray(new NameValuePair[0]));

		try {

			// 执行postMethod,调用远程HTTP Service
			int statusCode = httpClient.executeMethod(postMethod);// 200

			if (statusCode != HttpStatus.SC_OK) {
				responseMsg = "-999";
			} else {
				responseMsg = postMethod.getResponseBodyAsString().trim();
			}

		} catch (HttpException e) {
			responseMsg = "-998";
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			responseMsg = "-997";
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			responseMsg = "-996";
			logger.error(e.getMessage(), e);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	private static String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
		if (data == null || data.isEmpty()) {
			return "";
		}
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8") + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	public static void main(String[] args) {
		System.out.println(getHttp("http://www.baidu.com", null));
	}

}