package com.frame.base.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;

import com.frame.base.utils.StringUtil;

public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	public static String getExceptionMsg(Exception e, Logger logger) {
		if (e instanceof FrontShowException) {
			if(!StringUtil.isBlank(e.getMessage())) {
                logger.info(e.getMessage());
				return e.getMessage();
			} else {
				return "未知错误，请通知管理员.";
			}
		} else {
			logger.error(e.getMessage(), e);
			return "系统错误，请通知管理员.";
		}
	}
	
}
