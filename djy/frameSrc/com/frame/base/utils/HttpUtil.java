package com.frame.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class HttpUtil {
	
	/**
	 * 模拟 http、https 的get请求
	 * @param url 请求的url路径
	 * @param param 请求参数，为键值对，传入的值不需要提前UrlEncode编码。
	 * @param charset 字符集，如："utf-8"
	 * @return 
	 */
	public static String doGet(String url, Map<String, String> param, String charset) {
		
		String result = "";
		BufferedReader in = null;
		
		try {
			
			StringBuilder urlSb = new StringBuilder(url);
			if (url.indexOf("?") == -1)
				urlSb.append( "?" );
			
			boolean first = true;
			for ( String key : param.keySet() ) {
				if (first) {
					int tmp = url.lastIndexOf("?");
					if ( tmp != -1 && tmp != (url.length()-1) )
						urlSb.append( "&" );
					first = false;
				} else {
					urlSb.append( "&" );
				}
				urlSb.append( key ).append( "=" ).append( urlEncode(param.get(key), charset) );
			}
			
			URL realUrl = new URL(urlSb.toString());
			
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			
			if ( url.startsWith("https://") ) {
				// 设置https相关属性
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
				((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
				((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier());
			}
			
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 连接主机的超时时间（单位：毫秒）：30秒
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			// 从主机读取数据的超时时间（单位：毫秒）：30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			
			// 建立实际的连接
			conn.connect();
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	/**
	 * 模拟 http、https 的post请求
	 * @param url 请求的url路径
	 * @param param 请求参数，为键值对，传入的值不需要提前UrlEncode编码。
	 * @param charset 字符集，如："utf-8"
	 * @return 
	 */	
	public static String doPost(String url, Map<String, String> param, String charset) {
		
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		
		try {
			URL realUrl = new URL(url);
			
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			
			if ( url.startsWith("https://") ) {
				// 设置https相关属性
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
				((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
				((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier());
				((HttpsURLConnection) conn).setRequestMethod("POST");
			} else {
				((HttpURLConnection) conn).setRequestMethod("POST");
			}
			
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded"); 
			
			// 设置是否向 URLConnection 输出，因为这个是 post 请求，参数要放在 http、https 正文内，因此需要设为 true, 默认情况下是 false
			conn.setDoOutput(true);
			
			// 设置是否从 URLConnection 读入，默认情况下是true
			conn.setDoInput(true);
			
			// post 请求不能使用缓存 
			conn.setUseCaches(false);
			
			// 建立实际的连接，此句在开发中可以省略，因为getOutputStream会隐含的进行connect
			conn.connect();
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			
			// 组织请求参数
			StringBuilder paramSb = new StringBuilder();
			boolean first = true;
			for (String key : param.keySet()) {
				if (first) {
					first = false;
				} else {
					paramSb.append( "&" );
				}
				paramSb.append( key ).append( "=" ).append( urlEncode(param.get(key), charset) );
			}
			
			// 发送请求参数
			out.print(paramSb);
			
			// flush输出流的缓冲，刷新对象输出流，将任何字节都写入潜在的流中
			out.flush();
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	

	//===============================================================================
	
	private static String urlEncode(String str, String charset){
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException ex) {
			return str;
		}  
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
		
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
		
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
}
