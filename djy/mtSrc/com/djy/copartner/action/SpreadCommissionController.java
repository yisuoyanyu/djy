package com.djy.copartner.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.copartner.vo.SpreadUserVo;
import com.djy.fin.enumtype.FinChargeType;
import com.djy.fin.model.FinCharge;
import com.djy.fin.service.FinChargeService;
import com.djy.spread.model.SpreadCommission;
import com.djy.spread.service.SpreadCommissionService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.web.controller.WebController;

@Controller
@RequestMapping("/copartner/spreadCommission")
public class SpreadCommissionController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(SpreadCommissionController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private SpreadCommissionService spreadCommissionService;
	
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	@Autowired
	private FinChargeService finChargeService;
	
	@RequestMapping("/testRealCommission.do")
    public String testRealCommission(HttpServletRequest request,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
		FinCharge finCharge = finChargeService.get(17);
		ConsumeOrder consumeOrder = consumeOrderService.get(17);
		if ( finCharge.getType() == FinChargeType.consumeOrder.getId() ) {	// 会员消费

            consumeOrder.setStatus(ConsumeOrderStatus.paySuccess.getId());
			consumeOrderService.update(consumeOrder);
			
		if (consumeOrderService.isFirstBuy(consumeOrder.getUser())) {//是否为第一次购买信息
			
			// 从此处开始调用测试ACtion
			spreadCommissionService.dealFirstPaySuccess(finCharge.getConsumeOrder());
			
		}
		
		}
		List<SpreadCommission> spreadCommissions = spreadCommissionService.getCommByUser(user, 1);
	    m.addAttribute("spreadCommissions", spreadCommissions);
	    m.addAttribute("page", 1);
		m.addAttribute("totalPages", 2);
		m.addAttribute("lastPage", 0);
		m.addAttribute("nextPage", 2);
		m.addAttribute("startShowPage", 1);
		m.addAttribute("endShowPage", 2);
	    return "/copartner/spreadCommission/myCommission";
	}
	
	@RequestMapping("/myCommission.do")
	public String myCommission(HttpServletRequest request,
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
  		User user = userService.getById(userId);
  		
        int pageSize = 10;                              //每页显示记录数
		long totalItems = spreadCommissionService.getTotalNumByUser(user);//全部的推广的佣金数量
	    int startNum = 0;
		int endNum = 0;
		  
		int totalPages = 0;  //总页数
		
		startNum = (page - 1)*10+1;
		
		if (totalItems%pageSize == 0) {
			totalPages = ((int)totalItems/pageSize);
			endNum = page*10;
		}else {
			totalPages = ((int)totalItems/pageSize)+1;
			if (page == totalPages) {
				endNum = (page - 1)*10 + (int)(totalItems%pageSize);
			}else {
				endNum = page*10;
			}
			
		}
		
		int lastPage = 0;                               //上一页页码  等于1就不在前端显示
		int nextPage = 1;                               //下一页页码  等于1就不在前端显示
		
		if (page != 1) {
			lastPage = page-1;
		}
		
		if (page != totalPages) {
			nextPage = page+1;
		}
		
		int startShowPage = 1;                           //select中可选择的第一项
		int endShowPage = 1;                             //select中可选择的最后一项
		
		if (totalPages < 10) {//表示总页数不足10页
			startShowPage = 1;
			endShowPage = totalPages;
		}else {
			if (page - 2 <= 0) {
				startShowPage = 1;
			}else {
				startShowPage = page -2;
			}
			
			if (page + 7 < totalItems) {
				if (page + 7 < 10) {
					endShowPage = 10;
				}else {
					endShowPage = page + 7;
				}
				
			}else {
				endShowPage = totalPages;
				startShowPage = totalPages - 9;
			}
		}
		
		
		List<SpreadCommission> spreadCommissions = spreadCommissionService.getCommByUser(user, page);
		    m.addAttribute("spreadCommissions", spreadCommissions);
		    m.addAttribute("page", page);
			m.addAttribute("totalPages", totalPages);
			m.addAttribute("lastPage", lastPage);
			m.addAttribute("nextPage", nextPage);
			m.addAttribute("startShowPage", startShowPage);
			m.addAttribute("endShowPage", endShowPage);
		return "/copartner/spreadCommission/myCommission";
	}
}
