package com.djy.copartner.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.djy.user.model.User;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatUserService;
import com.frame.base.utils.HttpUtil;
import com.frame.base.web.controller.WebController;
import com.frame.wechat.api.MpApi;
import com.frame.wechat.api.json.UserInfo;
import com.frame.wechat.config.WechatParameter;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/copartner")
public class CopartnerCallbackController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(CopartnerCallbackController.class);
	
	@Autowired
	private WechatUserService wechatUserService;

	@RequestMapping(value = "/Oauth2.do")
	public String Oauth2(
			@RequestParam(value="copId", required=false) Integer copId,
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model m
			) throws Exception {
		
		String contextPath = request.getContextPath();
		
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// 静默授权
		String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		   Map<String, String> param = new HashMap<>(); 
		   param.put("appid", WechatParameter.appID);
		   param.put("secret", WechatParameter.appsecret);
		   param.put("code", request.getParameter("code"));
           param.put("grant_type", "authorization_code");
		String json = HttpUtil.doGet(get_access_token_url, param, "utf-8");
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		String openid = "";
		// 避免二次授权，code只能验证一次
		try {
			openid = jsonObject.getString("openid");
			if ( !openid.equals("") )
				session.setAttribute("openid", openid);
		} catch (Exception e) {
			openid = session.getAttribute("openid").toString();
		}
		
		WechatUser wechatUser = wechatUserService.getByOpenID(openid);
		
		try {
			
			if ( wechatUser != null ) {
				if (wechatUser.getSubscribe()==0) {	// 取消关注的情况
					// response.sendRedirect( contextPath + "/point/qrCode/showDjyQrcod.do" );
					response.sendRedirect( WechatParameter.serverUrl + "point/qrCode/showDjyQrcod.do" );
					return null;
				}
			} else {
				
				UserInfo userInfo = MpApi.getUserInfo(openid);
				
				if ( !"1".equals( userInfo.getSubscribe() ) ) {	// 当前没关注
					// response.sendRedirect( contextPath + "/point/qrCode/showDjyQrcod.do" );
					response.sendRedirect( WechatParameter.serverUrl + "point/qrCode/showDjyQrcod.do" );
					return null;
				}
				
				wechatUser = wechatUserService.createByUserInfo(userInfo, null);
			}
			
			User user = wechatUser.getUser();
			
			session.setAttribute("userId", user.getId());
			session.setAttribute("wechatUserId", wechatUser.getId());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		// response.sendRedirect( contextPath + "/copartner/index.do" );
		if (null == copId) {
			response.sendRedirect( WechatParameter.serverUrl + "copartner/index.do" );
		}else {
			response.sendRedirect( WechatParameter.serverUrl + "copartner/copartnerDetail.do?id="+copId );
		}
		
		
		return null;
		
	}

	/**
	 * 输出字符
	 */
	protected void out(String str, HttpServletResponse response) {
		Writer out = null;
		try {
			response.setContentType("text/xml;charset=UTF-8");
			out = response.getWriter();
			out.append(str);
			out.flush();
		} catch (IOException e) {
			// ignore
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

}
