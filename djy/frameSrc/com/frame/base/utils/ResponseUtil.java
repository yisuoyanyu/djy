package com.frame.base.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class ResponseUtil {
	
	
	public static void responseFile(byte[] data, String filename) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		
		String urlFilename = urlEncode(filename, "UTF-8");
		String rtnFilename = "filename=\"" + urlFilename + "\"";
		
		String userAgent = request.getHeader("User-Agent");
		if ( userAgent != null ) {
			userAgent = userAgent.toLowerCase();
			if ( userAgent.indexOf("msie") != -1 ) {				// IE浏览器，只能采用URLEncoder编码
				rtnFilename = "filename=\"" + urlFilename + "\"";
			} else if ( userAgent.indexOf("opera") != -1 ) {		// Opera浏览器只能采用filename*
				rtnFilename =  "filename*=UTF-8\''" + urlFilename;
			} else if ( userAgent.indexOf("safari") != -1 ) {		// Safari浏览器，只能采用ISO编码的中文输出
				rtnFilename = "filename=\"" + iosEncode(filename, "ISO8859-1") + "\"";  
			} else if (userAgent.indexOf("applewebkit") != -1 ) {	// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
				rtnFilename = "filename=\"" + mimeEncode(filename, "UTF-8") + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) {		// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
				rtnFilename = "filename*=UTF-8''" + urlFilename;
			}
		}
		
		response.setHeader("Content-disposition", "attachment;" + rtnFilename);
		
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			output.write( data );
		} catch(IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	//------------------------------------------------------

	private static String urlEncode(String str, String charset){
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException ex) {
			return str;
		}  
	}
	
	private static String iosEncode(String str, String charset) {
		try {
			return new String(str.getBytes("utf-8"), charset);
		} catch(UnsupportedEncodingException ex) {
			return str;
		}
	}
	
	
	private static String mimeEncode(String str, String charset) {
		try {
			return MimeUtility.encodeText(str, charset, "B");
		} catch(UnsupportedEncodingException ex) {
			return str;
		}
	}
	
}
