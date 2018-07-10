package com.frame.base.utils;

import java.util.Date;

/**
 * 编号生成器
 * @author puzd
 *
 */
public class SnGenerator {
	private static Integer step = 1;
	
	private static String formatStep(int i) {
		if (i < 10) {
			return "000" + i;
		} else if (i < 100) {
			return "00" + i;
		} else if (i < 1000) {
			return "0" + i;
		} else {
			return String.valueOf(i);
		}
	}
	
	/**
	 * 生成编号
	 * @param prefix
	 *   前缀
	 * @return
	 */
	public static String generate(String prefix) {
		return prefix + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + formatStep(step++ % 10000);
	}
}
