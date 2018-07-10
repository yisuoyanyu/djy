package com.djy.partner.action;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/partner/user/")
public class PartnerUserController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(PartnerUserController.class);
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 通过手机号查找用户
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/getUserByMobile")
	@ResponseBody
	public Result getUserByMobile(
			@RequestParam(value="mobile", required=false) String mobile ,
			@RequestParam(value="coPartnerEmplId", required=false) Integer coPartnerEmplId) {
		
		try {
			if (!StringUtil.isBlank(mobile)) {
				Map<String, Object> map = new HashMap<String, Object>();
				User user = userService.getUserByMobile(mobile);
				if (user != null) {
					//判断该手机号查询到的会员是否已被匹配过
					if (user.getSysEmpl() != null || user.getCoPartner() != null
							|| (user.getCoPartnerEmpl() != null && user.getCoPartnerEmpl().getId() != coPartnerEmplId) ) {
						return new Result(false, "您输入的会员手机已被使用，请重新输入会员手机！");
					}
					
					map.put("userId", user.getId());
					map.put("nickName", user.getWechatUser().getNickname());
					return new Result(true, map);
				} else {
					return new Result(false, "您输入的会员手机号错误，请重新输入会员手机！");
				}
			} else {
				return new Result(false, "");
			}
			
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "程序错误，请联系管理员！");
		}
	}
	
	
}
