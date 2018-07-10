package com.djy.point.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/point/help")
public class HelpController extends WebController{
	private static Logger logger = LoggerFactory.getLogger(HelpController.class);

	@RequestMapping("/helpCenter.do")
	public String helpCenter() {
		return "/point/help/helpCenter";
	}
}
