package com.frame.base.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

public class SessionUtil {

	public static Object getAttr(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return WebUtils.getSessionAttribute(request, name);
	}
	
	
	public static void setAttr(String name, Object val) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WebUtils.setSessionAttribute(request, name, val);
	}
	
	
	public static void removeAttr(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(name);
		}
	}
	
	
	public static void removeAllAttr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			Enumeration<String> en = session.getAttributeNames();
			while (en.hasMoreElements()) {
				String attr = (String) en.nextElement();
				session.removeAttribute(attr);
			}
		}
	}
	
	
	public static void invalidate() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
	
}
