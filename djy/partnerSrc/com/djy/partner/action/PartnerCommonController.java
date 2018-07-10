package com.djy.partner.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.service.CoPartnerService;
import com.djy.co.service.CoSettleOrderService;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.partner.vo.CoPartnerVo;
import com.djy.user.service.UserService;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/partner/common/")
public class PartnerCommonController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCommonController.class);
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private CoSettleOrderService coSettleOrderService;
	
	/**
	 * 跳转到系统用户管理后台首页
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpSession session, Model m) {
		
		CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
		
		CoPartner coPartner = coPartnerService.get(coPartnerVo.getId());
		
		m.addAttribute("coPartner", coPartner);
		
		return "/partner/common/index";
	}
	
	/**
	 * 跳转首页后加载订单统计数据
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpSession session,Model m) {
		try {
			
			CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
			CoPartner coPartner = coPartnerService.get(coPartnerVo.getId());
			
			Date today = new Date();
			Date yesterday = DateUtil.addDay(today, -1);
			
			Date todayMin = DateUtil.getFirstTimeOfDate(today);
			Date todayMax = DateUtil.getLastTimeOfDate(today);
			
			Date yesterdayMin = DateUtil.getFirstTimeOfDate(yesterday);
			Date yesterdayMax = DateUtil.getLastTimeOfDate(yesterday);
			
			Map<String, Object> todayParam = new HashMap<String, Object>();
			todayParam.put("coPartnerId", coPartnerVo.getId());
			todayParam.put("timeStart", todayMin);
			todayParam.put("timeEnd", todayMax);
			
			Map<String, Object> yesterdayParam = new HashMap<String, Object>();
			yesterdayParam.put("coPartnerId", coPartnerVo.getId());
			yesterdayParam.put("timeStart", yesterdayMin);
			yesterdayParam.put("timeEnd", yesterdayMax);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("coPartnerId", coPartnerVo.getId());
			
			//会员消费订单
			Integer consumeOrderNumAddToday = consumeOrderService.getAllConsumeOrderNum(todayParam).intValue();
			Integer consumeOrderNumAddYesterday = consumeOrderService.getAllConsumeOrderNum(yesterdayParam).intValue();
			Integer consumeOrderNumAddTotal = consumeOrderService.getAllConsumeOrderNum(param).intValue();
			
			//会员消费金额
			double consumeAmountToday = consumeOrderService.getConsumeOrderAmount(todayParam);
			double consumeAmountYesterday = consumeOrderService.getConsumeOrderAmount(yesterdayParam);
			double consumeAmountTotal = consumeOrderService.getConsumeOrderAmount(param);
			
			//商家为每单结算模式
			if (coPartner.getCoMode() == CoPartnerMode.perOrder.getId()) {
				//平台结算金额
				double coSettleAmountToday = coSettleOrderService.getCoSettleOrderAmount(todayParam);
				double coSettleAmountYesterday = coSettleOrderService.getCoSettleOrderAmount(yesterdayParam);
				double coSettleAmountTotal = coSettleOrderService.getCoSettleOrderAmount(param);
				m.addAttribute("coSettleAmountToday", coSettleAmountToday);
				m.addAttribute("coSettleAmountYesterday", coSettleAmountYesterday);
				m.addAttribute("coSettleAmountTotal", coSettleAmountTotal);
			}
			
			//被邀会员
			Integer consumeUserAddToday = userService.getFirstConsumeUserNumByCoParnerId(todayParam).intValue();
			Integer consumeUserAddYesterday = userService.getFirstConsumeUserNumByCoParnerId(yesterdayParam).intValue();
			Integer consumeUserAddTotal = userService.getFirstConsumeUserNumByCoParnerId(param).intValue();
			
			//被邀会员消费订单
			Integer consumeOrderAddToday = consumeOrderService.getConsumeOrderNumByCoParnerId(todayParam).intValue();
			Integer consumeOrderAddYesterday = consumeOrderService.getConsumeOrderNumByCoParnerId(yesterdayParam).intValue();
			Integer consumeOrderAddTotal = consumeOrderService.getConsumeOrderNumByCoParnerId(param).intValue();
			
			//被邀会员消费金额
			double invitedConsumeAmountToday = consumeOrderService.getInvitedConsumeOrderAmountByCoParnerId(todayParam);
			double invitedConsumeAmountYesterday = consumeOrderService.getInvitedConsumeOrderAmountByCoParnerId(yesterdayParam);
			double invitedConsumeAmountTotal = consumeOrderService.getInvitedConsumeOrderAmountByCoParnerId(param);
			
			//被邀平台结算金额
			double invitedCoSettleAmountToday = coSettleOrderService.getInvitedCoSettleAmountByCoParnerId(todayParam);
			double invitedCoSettleAmountYesterday = coSettleOrderService.getInvitedCoSettleAmountByCoParnerId(yesterdayParam);
			double invitedCoSettleAmountTotal = coSettleOrderService.getInvitedCoSettleAmountByCoParnerId(param);
			
			m.addAttribute("consumeOrderNumAddToday", consumeOrderNumAddToday);
			m.addAttribute("consumeOrderNumAddYesterday", consumeOrderNumAddYesterday);
			m.addAttribute("consumeOrderNumAddTotal", consumeOrderNumAddTotal);
			
			m.addAttribute("consumeAmountToday", consumeAmountToday);
			m.addAttribute("consumeAmountYesterday", consumeAmountYesterday);
			m.addAttribute("consumeAmountTotal", consumeAmountTotal);
			
			m.addAttribute("consumeUserAddToday", consumeUserAddToday);
			m.addAttribute("consumeUserAddYesterday", consumeUserAddYesterday);
			m.addAttribute("consumeUserAddTotal", consumeUserAddTotal);
			
			m.addAttribute("consumeOrderAddToday", consumeOrderAddToday);
			m.addAttribute("consumeOrderAddYesterday", consumeOrderAddYesterday);
			m.addAttribute("consumeOrderAddTotal", consumeOrderAddTotal);
			
			m.addAttribute("invitedConsumeAmountToday", invitedConsumeAmountToday);
			m.addAttribute("invitedConsumeAmountYesterday", invitedConsumeAmountYesterday);
			m.addAttribute("invitedConsumeAmountTotal", invitedConsumeAmountTotal);
			
			m.addAttribute("invitedCoSettleAmountToday", invitedCoSettleAmountToday);
			m.addAttribute("invitedCoSettleAmountYesterday", invitedCoSettleAmountYesterday);
			m.addAttribute("invitedCoSettleAmountTotal", invitedCoSettleAmountTotal);
			
			
			m.addAttribute("coPartner", coPartner);
			m.addAttribute("sysDeposit", CoPartnerMode.sysDeposit.getId());
			
			return "/partner/common/home";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
}
