package com.frame.base.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 或由 判断是否为null或长度为0或由空白符（空格、\t、\n、\f、\r）构成
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj){
		if (obj == null)
			return true;
		
		String temp = obj.toString().trim();
		if (StringUtils.isBlank(temp))
			return true;
		
		return false;
	}
	
	/**
	 * 判断是否为null、""、"null"
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		
		String temp = obj.toString().trim();
		if ("".equals(temp) || "null".equals(temp))
			return true;
		
		return false;
	}
	
	
	/**
	 * 判断字符串数组 String[]是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isStringArrayEmpty(String[] obj) {
		if (obj == null)
			return true;
		try {
			String[] strs = obj;
			if (strs.length > 0)
				return false;
			else
				return true;
		} catch (Exception e) {
			return true;
		}
	}
	
	
	/**
	 * 把null、""、"null"格式化为""，其他值格式化为字符串
	 * @param obj
	 * @return
	 */
	public static String nullToBlank(Object obj) {
		if (obj == null)
			return "";
		String temp = obj.toString().trim();
		if ("".equals(temp) || "null".equals(temp)) {
			return "";
		}
		return temp;
	}

	
	/**
	 * 把obj格式化为字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null)
			return "";
		return ToStringBuilder.reflectionToString(obj);
	}
	
	
	/**
	 * 数组转化为字符串
	 * @param arr
	 * @param split
	 * @return
	 */
	public static String arrayToString(Object[] arr, String split) {
		if (arr == null)
			return null;
		
		boolean isFirst = true;
		StringBuffer sb = new StringBuffer();
		for ( Object obj : arr ) {
			if (!isFirst) {
				sb.append(split);
			} else {
				isFirst = false;
			}
			sb.append(obj);
		}
		return sb.toString();
	}
	
	
	/**
	 * 数组转化为字符串，每个元素的分隔符为";"
	 * @param arr
	 * @return
	 */
	public static String arrayToString(Object[] arr) {
		return arrayToString(arr, ";");
	}
	
	
	/**
	 * List转化为字符串
	 * @param arr
	 * @param split
	 * @return
	 */
	public static String arrayToString(List<?> list, String split) {
		if (list == null)
			return null;
		
		return arrayToString(list.toArray(), split);
	}
	
	
	/**
	 * 获取MD5加密串
	 * @author beeyon 2012-07-31
	 * @param value
	 * @return
	 */
	public static String md5s(String value) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] bytearr = md5.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytearr.length; i++) {
			if (Integer.toHexString(0xFF & bytearr[i]).length() == 1) {
				sb.append("0").append(Integer.toHexString(0xFF & bytearr[i]));
			} else
				sb.append(Integer.toHexString(0xFF & bytearr[i]));
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 判断字符串是不是手机号
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		return str.matches("^(13|15|18|17)\\d{9}$");	
	}
	
	//-------------------------------------------------------------
	
	/**
	 * 手机号转为带"*"掩码的字符串
	 * @param mobile
	 * @return
	 */
	public static String formatMobileToStar(String mobile) {
		if ( mobile.length()<11 )
			return "****";
		return mobile.substring(0, 3)+"****"+mobile.substring(7);
	}
	
	
	/**
	 * 姓名转化为中间带"*"掩码的字符串
	 * @param name
	 * @return
	 */
	public static String formatNameToStar(String name) {
		if (StringUtils.isBlank(name))
			return "";
        return name.substring(0, 1) + "***" + name.substring(name.length()-1);
    }
	
	//-------------------------------------------------------------
	
	private static boolean isNotEmojiChar(char codePoint) {
		return codePoint == 0x0 
				|| codePoint == 0x9 
				|| codePoint == 0xA 
				|| codePoint == 0xD 
				|| (codePoint >= 0x20 && codePoint <= 0xD7FF)
				|| (codePoint >= 0xE000 && codePoint <= 0xFFFD) 
				|| (codePoint >= 0x10000 && codePoint <= 0x10FFFF);
	}
	
	
	/**
	 * 检测是否有emoji字符
	 * @param source
	 * @return
	 */
	private static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source))
			return false;
		
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if ( ! isNotEmojiChar( codePoint ) ) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		
		if ( ! containsEmoji( source ) ) {
			return source;
		}
		
		StringBuilder buf = new StringBuilder();
		
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if ( isNotEmojiChar(codePoint) ) {
				buf.append(codePoint);
			}
		}
		
		return buf.toString();
	}
	
	//-------------------------------------------------------------
	
	/**
	 * 过滤特殊字符
	 * @param str
	 * @return
	 */
	public static String filterSpecial(String str) {
		if (str.trim().isEmpty()) {
			return str;
		}
		String pattern = 
				"[\ud83c\udc00-\ud83c\udfff]" 
				+ "|[\ud83d\udc00-\ud83d\udfff]" 
				+ "|[\u2600-\u27ff]" 
				+ "|[\ud800\udc00-\udbff\udfff\ud800-\udfff]";
		String reStr = "";
		Pattern emoji = Pattern.compile(pattern);
		Matcher emojiMatcher = emoji.matcher(str);
		str = emojiMatcher.replaceAll(reStr);
		return str;
	}
	
	//-------------------------------------------------------------
	
	/**
	 * URLEncode
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String urlEncode(String str, String charset) {
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException ex) {
			return str;
		}
	}
	
	/**
	 * URLEncode
	 * @param str
	 * @return
	 */
	public static String urlEncode(String str) {
		return urlEncode(str, "utf-8");
	}
	
	//-------------------------------------------------------------
	
}
