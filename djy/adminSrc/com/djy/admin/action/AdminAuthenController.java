package com.djy.admin.action;


import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.admin.vo.SysUserVo;
import com.djy.sys.model.SysResource;
import com.djy.sys.model.SysUser;
import com.djy.sys.service.SysUserService;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.SessionUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/authen/")
public class AdminAuthenController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminAuthenController.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 登录管理后台
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model m) {
		return "/admin/authen/login";
	}
	
	
	/**
	 * 登录
	 * @param account
	 *     用户名
	 * @param password
	 *     密码
	 * @param captcha
	 *     图形验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doLogin")
	@ResponseBody
	public Result doLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("account") String account,
			@RequestParam("password") String password,
			@RequestParam("captcha") String captcha,
			HttpSession session
	) {
		try {
			
			if ( StringUtil.isEmpty(account) ) {
				return new Result(false, "请输入账号!");
			}
			
			if ( StringUtil.isEmpty(password) ) {
				return new Result(false, "请输入密码!");
			}
			
			if ( StringUtil.isEmpty(captcha) ) {
				return new Result(false, "请输入图形验证码!");
			}
			
			// 校验图形验证码
			String sessionCode = (String) SessionUtil.getAttr( ConfigUtil.get("sys.session.captcha") );
			String code = StringUtil.isEmpty(sessionCode)? "": sessionCode.toLowerCase();
			if ( !code.equals(captcha.toLowerCase()) ) {
				return new Result(false, "验证码错误!");
			}
			
			// 验证账号、密码是否正确
			SysUser sysUser = sysUserService.checkLogin(account, password);
			if (sysUser == null) {
				return new Result(false, "账号密码错误!");
			}
			
			// 清除之前的session缓存
			SessionUtil.removeAllAttr();
			
			// 缓存用户信息
			SessionUtil.setAttr(ConfigUtil.get("sys.session.loginUser"), SysUserVo.transfer(sysUser));
			
			// 缓存用户权限
			Set<SysResource> resources = sysUser.getSysRole().getSysResources();
			for (SysResource resource : resources) {
				SessionUtil.setAttr(resource.getCode(), resource.getCode());
            }
			
			return new Result(true, "登录成功！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请刷新页面！");
		}
		
	}
	
	/**
	 * 退出管理后台
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doLogout")
	@ResponseBody
	public Result doLogout() {
		SessionUtil.invalidate();
		return new Result(true, "注销登录成功！");
	}
	
}
