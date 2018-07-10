package com.djy.copartner.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/copartner/wechatNews")
public class WechatNewsController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(WechatNewsController.class);
	
	@RequestMapping("/saveMoney.do")
	public String saveMoney(){
		
		return "/copartner/wechatNews/saveMoney";
		
		
	}
	
	@RequestMapping("/oilStationRecruitment.do")
	public String oilStationRecruitment(){
		
		return "/copartner/wechatNews/oilStationRecruitment";
		
		
	}
	
}
