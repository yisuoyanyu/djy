package com.djy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;
import com.frame.wechat.config.WechatParameter;




/**
 * 获取请求方法和参数
 */
@Component
public class PermissionFilter implements Filter{

	public void destroy() {
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		String contextPath = ((HttpServletRequest)request).getContextPath();
		
		String url = ((HttpServletRequest)request).getRequestURI();
		if ( !StringUtil.isEmpty(contextPath) )
			url = url.substring( contextPath.length() );
		
		if ( url.startsWith("/notice/") ) {				// 支付监听平台
			
			if ( url.endsWith("/wxpay/notifyUrl.do")  ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			}
			
		} if ( url.startsWith("/admin/") ) {			// 平台工作人员管理后台
			
			if ( url.endsWith("login.do") || url.endsWith("randomCode.do") || url.endsWith("doLogin.json") ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			}
			
			if ( session.getAttribute( ConfigUtil.get("sys.session.loginUser") ) == null ) {
				if ( url.endsWith(".do") || url.endsWith(".json")) {
					((HttpServletResponse)response).sendRedirect( contextPath + "/admin/authen/login.do" );
				}
				return;
			}
			
		} else if ( url.startsWith("/partner/") ) {			// 商家人员管理后台
			
			if ( url.endsWith("login.do") || url.endsWith("randomCode.do") || url.endsWith("doLogin.json") ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			}
			
			if ( session.getAttribute( ConfigUtil.get("sys.session.loginPartner") ) == null ) {
				if ( url.endsWith(".do") || url.endsWith(".json")) {
					((HttpServletResponse)response).sendRedirect( contextPath + "/partner/authen/login.do" );
				}
				return;
			}
			
		} else if ( url.startsWith("/empl/") ) {			// 职员管理后台
			
			if ( url.endsWith("login.do") || url.endsWith("randomCode.do") || url.endsWith("doLogin.json") ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			}
			
			if ( session.getAttribute( ConfigUtil.get("sys.session.loginEmpl") ) == null ) {
				if ( url.endsWith(".do") || url.endsWith(".json")) {
					((HttpServletResponse)response).sendRedirect( contextPath + "/empl/authen/login.do" );
				}
				return;
			}
			
		} else if ( url.startsWith("/point/") ) {        // 积分系统
			
			if ( url.endsWith("Oauth2.do") 
					|| url.endsWith("/myQrcod.do") ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			}
			if ( session.getAttribute("userId") != null ) {
				//满足条件就继续执行
				chain.doFilter(request, response);
				return;
			} else {
				String redirect = "https://open.weixin.qq.com/connect/oauth2/authorize?" 
						+ "appid=" + WechatParameter.appID 
						+ "&redirect_uri=" + StringUtil.urlEncode( WechatParameter.serverUrl ) + "point%2fOauth2.do" 
						+ "&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";
				((HttpServletResponse)response).sendRedirect(redirect);
				return;
			}
			
		}
		
		chain.doFilter(request, response);
		
	}

	
}
