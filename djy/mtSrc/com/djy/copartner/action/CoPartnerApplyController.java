package com.djy.copartner.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.enumtype.CoPartnerApplyStatus;
import com.djy.co.model.CoPartnerApply;
import com.djy.co.service.CoPartnerApplyService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/copartner/coPartnerApply")
public class CoPartnerApplyController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(CoPartnerApplyController.class);
	
	@Autowired
	private CoPartnerApplyService coPartnerApplyService;
	@Autowired
	private UserService userService;
	
	@ModelAttribute("/applyCopartner")
	public CoPartnerApply getCoPartnerApply(
		@RequestParam(value = "id", required = false) Integer id
	) {
		if (id != null) {
			return this.coPartnerApplyService.get(id);
		}
		return new CoPartnerApply();
	}
	
	@RequestMapping("/coApply.do")
	public String coApply(HttpSession session, Model m) {

		return "/copartner/coPartnerApply/coApply";
		
	}
	
	@RequestMapping("/saveApplyCopartner.do")
	@ResponseBody
	public Result saveApplyCopartner(
			@ModelAttribute("applyCopartner") CoPartnerApply coPartnerApply,
			HttpSession session,Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		Date now = new Date();
		
		coPartnerApply.setUser(user);
		coPartnerApply.setInsertTime(now);
		coPartnerApply.setStatus(CoPartnerApplyStatus.nohandle.getId());
		try {
			coPartnerApplyService.save(coPartnerApply);
			return new Result(true, "提交成功");
		} catch (Exception e) {
			return new Result(false, "提交失败");
		}
		
	}
	
	@RequestMapping("/coApplyList.do")
	public String coApplyList(HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		
		List<CoPartnerApply> coPartnerApplies = coPartnerApplyService.findByUser(user);
		
		m.addAttribute("coPartnerApplies", coPartnerApplies);
		
		return "/copartner/coPartnerApply/coApplyList";
		
	}
}
