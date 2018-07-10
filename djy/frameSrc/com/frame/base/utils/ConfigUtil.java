package com.frame.base.utils;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author: puzd
 * @date: 2016/10/26
 * @version: v1.0.
 * @description:
 *
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		try {
			String val = bundle.getString(key);
			return val;
		} catch(Exception e) {
			return null;
		}
	}

}
