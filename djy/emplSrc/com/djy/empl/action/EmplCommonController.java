package com.djy.empl.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/empl/common/")
public class EmplCommonController extends WebController {
	
	
	/**
	 * 跳转到职员管理后台首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/empl/common/index";
	}
	
	/**
	 * 跳转首页后加载订单统计数据
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/home")
	public String home(Model m) {
		return "/empl/common/home";
	}
	
}
