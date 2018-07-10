package com.djy.copartner.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.copartner.vo.CopartnerVo;
import com.djy.copartner.vo.UserSiteMsgVo;
import com.djy.user.enumtype.MessageType;
import com.djy.user.model.User;
import com.djy.user.model.UserSiteMsg;
import com.djy.user.service.UserService;
import com.djy.user.service.UserSiteMsgService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/copartner/userSiteMsg")
public class UserSiteMsgController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(UserSiteMsgController.class);
	
	@Autowired
	private UserSiteMsgService userSiteMsgService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/msg.do")
	public String msg(HttpSession session,Model m) {
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			User user = userService.get(userId);
			long msgSysNum = userSiteMsgService.countUnReadByUserId(user, MessageType.system.getId());
			long msgOrderNum = userSiteMsgService.countUnReadByUserId(user, MessageType.order.getId());
			long msgVoudiscountNum = userSiteMsgService.countUnReadByUserId(user, MessageType.coudiscount.getId());
			
			UserSiteMsg sysSiteMsg = userSiteMsgService.getRecUnRed(user, MessageType.system.getId());
			UserSiteMsg OrderMsg = userSiteMsgService.getRecUnRed(user, MessageType.order.getId());
			UserSiteMsg VouMsg = userSiteMsgService.getRecUnRed(user, MessageType.coudiscount.getId());
			
			m.addAttribute("msgSysNum",msgSysNum);
			m.addAttribute("msgOrderNum",msgOrderNum);
			m.addAttribute("msgVoudiscountNum",msgVoudiscountNum);
			
			m.addAttribute("sysSiteMsg", sysSiteMsg);
			m.addAttribute("OrderMsg", OrderMsg);
			m.addAttribute("VouMsg", VouMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/copartner/userSiteMsg/msg";
	}
	
	@RequestMapping("/msgList.do")
	public String msgList(
			@RequestParam(value="type", required=false) int type,
			HttpSession session,Model m){
		m.addAttribute("type", type);
		return "/copartner/userSiteMsg/msgList";
		
	}
	
	/*获取三种类型消息内容*/
	@RequestMapping("/getUserSiteMsg.do")
	@ResponseBody
	public List<UserSiteMsgVo> getUserSiteMsg(
			@RequestParam(value="type", required=false) int type,
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.get(userId);
		
		List<UserSiteMsg> userSiteMsgs = userSiteMsgService.getByPageType(page, type, user);
		
		List<UserSiteMsgVo> userSiteMsgVos = new ArrayList<>();
		
		for(int i = 0;i<userSiteMsgs.size();i++){
			UserSiteMsgVo userSiteMsgVo = UserSiteMsgVo.getUserSiteMsgVo(userSiteMsgs.get(i));
			userSiteMsgVos.add(userSiteMsgVo);
		}
		
		for(int i = 0;i<userSiteMsgs.size();i++){
			UserSiteMsg userSiteMsg = userSiteMsgs.get(i);
			userSiteMsg.setReadTime(new Date());
			userSiteMsgService.update(userSiteMsg);
		}
		
		return userSiteMsgVos;
		
	}
	
	/*修改已读状态*/
	@RequestMapping("/addReadTime.do")
	@ResponseBody
	public Result addReadTime(
			@RequestParam(value="msgId", required=false) int msgId,
			HttpSession session, Model m){
		
		UserSiteMsg userSiteMsg = userSiteMsgService.get(msgId);
		Date readTime = new Date();
		userSiteMsg.setReadTime(readTime);
		try {
			userSiteMsgService.update(userSiteMsg);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			return new Result(false, "增加失败");
		}
		
	}
}
