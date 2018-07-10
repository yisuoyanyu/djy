package com.djy.point.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.point.vo.WxConfigVo;
import com.djy.user.enumtype.UserType;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/point")
public class PointController extends WebController{
	private static Logger logger = LoggerFactory.getLogger(PointController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping("/index.do")
	public String index(HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		Integer busOrCus = 2;//0表示合作商家；1表示合作商家发展的顾客；2表示普通的顾客【直接通过二维码扫描进入系统的人】
//		if ((user.getSponsor() != null) || (user.getSponsor() != null && user.getCoPartner() != null && user.getCoPartner().getStatus() == CoPartnerStatus.freeze.getId())) {
//			busOrCus = 1;
//		}
//		if ((user.getSponsor() == null && user.getCoPartner() == null) || (user.getSponsor() == null && user.getCoPartner() != null && user.getCoPartner().getStatus() == CoPartnerStatus.freeze.getId())) {
//			busOrCus = 2;
//		}
//		if (user.getCoPartner() != null && user.getCoPartner().getStatus() == CoPartnerStatus.normal.getId()) {
//			busOrCus = 0;
//		}
		if (user.getType() == UserType.customer.getId()) {
			busOrCus = 1;
		} else if (user.getType() == UserType.copartner.getId()) {
        	busOrCus = 0;
        }
		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
		m.addAttribute("wxConfig", wxConfig);
		m.addAttribute("busOrCus", busOrCus);
		m.addAttribute("user", user);
		return "/point/index";
	}
	
	@RequestMapping("/bindUser.do")
	@ResponseBody
	public Result bindUser(HttpSession session) {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			User user = userService.get(userId);

			if (user.getMobile() != null) {
				return new Result(true, "用户已绑定手机号");
			} else {
				return new Result(false, "用户还未绑定手机号");
			}
		} catch (Exception e) {
			return new Result(false, "");
		}
	}
	
	@RequestMapping("/checkUserPhone.do")
	@ResponseBody
	public Result checkUserPhone(HttpSession session) {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			User userNow = userService.get(userId);
			
			String phone =  request.getParameter("phone");
			User user = userService.getUserByMobile(phone);

			if (user != null) {
				if (userNow.equals(user)) {
					return new Result(false, "对不起，您无法转积分给自己");
				} else {
					return new Result(true, "");
				}
			}else {
				return new Result(false, "该用户不存在,或者对方暂未绑定手机号");
			}
		} catch (Exception e) {
			return new Result(false, "");
		}
	}
}
