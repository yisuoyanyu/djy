package com.djy.point.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.sms.enumtype.SmsCodeScene;
import com.djy.sms.service.SmsCodeService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.exception.FrontShowException;
import com.frame.base.utils.RequestUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/point/bindPhone")
public class BindPhoneController extends WebController{
	private static Logger logger = LoggerFactory.getLogger(BindPhoneController.class);
	
    @Autowired
    private UserService userService;
    @Autowired
    private SmsCodeService smsCodeService;
    
    @RequestMapping("/bindPhone.do")
	public String bindPhone(HttpSession session, Model m) {
    	Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		m.addAttribute("user", user);
    	return "/point/bindPhone/bindPhone";
    }
    
    /**
	 * 发送短信验证码
	 * 
	 * @param scene
	 *            业务场景。1001—用户注册，1002—绑定手机。
	 * @param mobile
	 *            手机号码
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendSmsCode")
	@ResponseBody
	public Result sendSmsCode(
			@RequestParam("scene") Integer scene, 
			@RequestParam("mobile") String mobile,
			HttpSession session, HttpServletRequest request) throws Exception {

		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return new Result(false, "非法请求！");
		}

		String ip = RequestUtil.getIpAddr(request);

		try {
			String token = smsCodeService.sendSmsCode(scene, mobile, ip);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("mobile", mobile);
			data.put("token", token);
			return new Result(true, data);
		} catch (FrontShowException e) {
			System.out.println(e.getMessage());
			return new Result(false, e.getMessage());
		}

	}
	
	@RequestMapping("/bindingMobile")
	@ResponseBody
	public Result bindingMobile(
			@RequestParam("mobile") String mobile, 
			@RequestParam("smsToken") String smsToken,
			@RequestParam("smsCode") String smsCode, 
			HttpSession session, HttpServletRequest request) throws Exception {

		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			return new Result(false, "非法请求！");
		}

		User user = userService.get(userId);
		if (user == null) {
			return new Result(false, "非法用户请求！");
		}

		String ip = RequestUtil.getIpAddr(request);

		try {
			smsCodeService.validateSmsCode(SmsCodeScene.bindingMobile.getId(), mobile, smsToken, smsCode, ip);
		} catch (FrontShowException e) {
			return new Result(false, e.getMessage());
		}
		
		try {
			user.setMobile(mobile);
			userService.update(user);
		} catch (Exception e) {
			String msg = e.getMessage();
			return new Result(false, msg);
		}
		
		return new Result(true, "绑定成功");
	}
    
    @RequestMapping("/myPhoneNum.do")
  	public String myPhoneNum(HttpSession session, Model m) {
      	Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		m.addAttribute("user", user);
      	return "/point/bindPhone/myPhoneNum";
      }
}
