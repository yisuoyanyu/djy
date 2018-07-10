package com.djy.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.frame.base.utils.ConfigUtil;

public class UserUtil {
	
	public static Map<String, Object> getCurUser() {
		Map<String, Object> map = new HashMap<>();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		if (session.getAttribute(ConfigUtil.get("sys.session.loginUser")) != null) {
			map.put("type", "sysUser");
			map.put("user", session.getAttribute(ConfigUtil.get("sys.session.loginUser")));
			return map;
		}
		return null;
	}
	
}
