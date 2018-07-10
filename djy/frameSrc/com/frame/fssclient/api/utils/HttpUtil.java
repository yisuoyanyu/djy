package com.frame.fssclient.api.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

import java.security.cert.X509Certificate;

public class HttpUtil {
	
	public static InputStream doGetStream(String url, Map<String, String> param, String charset) {
		
		InputStream result = null;
		
		URLConnection conn = null;
		InputStream in = null;
		
		try {
			
			StringBuilder urlSb = new StringBuilder(url);
			
			if ( param != null ) {
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
			}
			
			URL realUrl = new URL(urlSb.toString());
			
			// 打开和URL之间的连接
			conn = realUrl.openConnection();
			
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
			//conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String referer = request.getHeader("referer");
			conn.setRequestProperty("referer", referer);
			
			// 连接主机的超时时间（单位：毫秒）：30秒
			// System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			conn.setConnectTimeout( 30 * 1000 );
			
			// 从主机读取数据的超时时间（单位：毫秒）：30秒
			// System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			conn.setReadTimeout( 30 * 1000 );
			
			// 建立实际的连接
			conn.connect();
			
			// 定义BufferedReader输入流来读取URL的响应
			in = conn.getInputStream();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[1024];
			int len = -1;
			while ( ( len = in.read( buffer ) ) != -1 ) {
				out.write(buffer, 0, len);
			}
			
			byte[] bytes = out.toByteArray();
			
            result = new ByteArrayInputStream( bytes );
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (conn != null) {
					if ( url.startsWith("https://") ) {
						((HttpsURLConnection) conn).disconnect();
					} else {
						((HttpURLConnection) conn).disconnect();
					}
				}
				
				if (in != null)
					in.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	/**
	 * 模拟 http、https 的get请求
	 * @param url 请求的url路径
	 * @param param 请求参数，为键值对，传入的值不需要提前UrlEncode编码。
	 * @param charset 字符集，如："utf-8"
	 * @return 
	 */
	public static String doGet(String url, Map<String, String> param, String charset) {
		
		String result = "";
		
		URLConnection conn = null;
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
			conn = realUrl.openConnection();
			
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
			
			//conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 连接主机的超时时间（单位：毫秒）：30秒
			// System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			conn.setConnectTimeout( 30 * 1000 );
			
			// 从主机读取数据的超时时间（单位：毫秒）：30秒
			// System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			conn.setReadTimeout( 30 * 1000 );
			
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
				if (conn != null) {
					if ( url.startsWith("https://") ) {
						((HttpsURLConnection) conn).disconnect();
					} else {
						((HttpURLConnection) conn).disconnect();
					}
				}
				if (in != null)
					in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	/**
	 * 模拟 http、https 的post请求，contentType为"application/x-www-form-urlencoded"
	 * @param url 请求的url路径
	 * @param param 请求参数，为键值对，传入的值不需要提前UrlEncode编码。
	 * @param charset 字符集，如："utf-8"
	 * @return 
	 */	
	public static String doPost(String url, Map<String, Object> param, String charset) {
		return doPost(url, param, 1, charset);
	}
	
	
	/**
	 * 模拟 http、https 的post请求
	 * @param url 请求的url路径
	 * @param param 请求参数，为键值对，传入的值不需要提前UrlEncode编码。
	 * @param contentType 以什么内容格式提交
	 *         1 — "application/x-www-form-urlencoded"；
	 *         2 — "application/json"；
	 *         3 — "multipart/form-data"；
	 * @param charset 字符集，如："utf-8"
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> param, int contentType, String charset) {
		
		String result = "";
		
		URLConnection conn = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		
		try {
			URL realUrl = new URL(url);
			
			// 打开和URL之间的连接
			conn = realUrl.openConnection();
			
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
			conn.setRequestProperty("Charsert", charset);
			
			String boundary = "";
			
			if (contentType == 1)
				conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			else if (contentType == 2)
				conn.setRequestProperty("Content-type", "application/json");
			else if (contentType == 3) {
				boundary = UUID.randomUUID().toString();
				conn.setRequestProperty("Content-type", "multipart/form-data;boundary=" + boundary);
			}
			
			// 设置是否向 URLConnection 输出，因为这个是 post 请求，参数要放在 http、https 正文内，因此需要设为 true, 默认情况下是 false
			conn.setDoOutput(true);
			
			// 设置是否从 URLConnection 读入，默认情况下是true
			conn.setDoInput(true);
			
			// post 请求不能使用缓存 
			conn.setUseCaches(false);
			
			// 建立实际的连接，此句在开发中可以省略，因为getOutputStream会隐含的进行connect
			conn.connect();
			
			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream( conn.getOutputStream() );
			
			
			if ( contentType == 1 ) {		// "application/x-www-form-urlencoded"
				// 组织请求参数
				StringBuilder paramSb = new StringBuilder();
				boolean first = true;
				for (String key : param.keySet()) {
					if (first) {
						first = false;
					} else {
						paramSb.append( "&" );
					}
					Object val = param.get(key);
					if ( val instanceof String) {
						paramSb.append( key ).append( "=" ).append( urlEncode(val.toString(), charset) );
					} else if ( val.getClass().isArray() ) {
						Object[] arr = (Object[]) val;
						for (int i=0; i<arr.length; i++) {
							paramSb.append( key ).append( "[]=" ).append( urlEncode(arr[i].toString(), charset) );
						}
					}
				}
				// 发送请求参数
				out.write( paramSb.toString().getBytes(charset) );
			} else if ( contentType == 2 ) {		// "application/json"
				JSONObject json = JSONObject.fromObject(param);
				// 发送请求参数
				out.write( json.toString().getBytes(charset) );
			} else if ( contentType == 3 ) {		// "multipart/form-data"
				String prefix = "--";
				String end = "\r\n";
				
				if ( param != null ) {
					
					for (String key : param.keySet()) {
						Object val = param.get(key);
						
						if ( val instanceof String) {
							out.write( (prefix + boundary + end).getBytes(charset) );
							out.write( ("Content-Disposition: form-data; name=\"" + key + "\"" + end).getBytes(charset) );
							out.write( ("Content-Type: text/plain; charset=" + charset + end).getBytes(charset) );
							out.write( ("Content-Transfer-Encoding: base64" + end).getBytes(charset) );
							out.write( end.getBytes(charset) );
							out.write( val.toString().getBytes() );
							out.write( end.getBytes(charset) );
						} else if ( val.getClass().isArray() ) {
							Object[] arr = (Object[]) val;
							for (int i=0; i<arr.length; i++) {
								out.write( (prefix + boundary + end).getBytes(charset) );
								out.write( ("Content-Disposition: form-data; name=\"" + key + "\"" + end).getBytes(charset) );
								out.write( ("Content-Type: text/plain; charset=" + charset + end).getBytes(charset) );
								out.write( ("Content-Transfer-Encoding: base64" + end).getBytes(charset) );
								out.write( end.getBytes(charset) );
								out.write( ((String) arr[i]).getBytes() );
								out.write( end.getBytes(charset) );
							}
						} else if ( val instanceof File ) {
							File file = (File) val;
							out.write( (prefix + boundary + end).getBytes(charset) );
							out.write( ("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + file.getName() + "\"" + end).getBytes(charset) );
							out.write( ("Content-Type: application/octet-stream; charset=" + charset + end).getBytes(charset) );
							out.write( end.getBytes(charset) );
							
							InputStream input = null;
							try {
								input = new FileInputStream(file);
								byte[] buffer = new byte[1024];
								int len = -1;
								while ( ( len = input.read( buffer ) ) != -1 ) {
							       out.write(buffer, 0, len);
							    }
							} catch (IOException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if (input != null)
									input.close();
							}
							
							out.write( end.getBytes(charset) );
						}
						
					}
					
				}
				
				// 结束标志
				out.write( (prefix + boundary + prefix + end).getBytes(charset) );
				
			}
			
			// flush输出流的缓冲，刷新对象输出流，将任何字节都写入潜在的流中
			out.flush();
			
			int responseCode;
			if ( url.startsWith("https://") ) {
				responseCode = ((HttpsURLConnection) conn).getResponseCode();
			} else {
				responseCode = ((HttpURLConnection) conn).getResponseCode();
			}
			if ( responseCode == 200 ) {
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输出流、输入流
			try {
				if (out != null)
					out.close();
				
				if (in != null)
					in.close();
				
				if (conn != null) {
					if ( url.startsWith("https://") ) {
						((HttpsURLConnection) conn).disconnect();
					} else {
						((HttpURLConnection) conn).disconnect();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
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
