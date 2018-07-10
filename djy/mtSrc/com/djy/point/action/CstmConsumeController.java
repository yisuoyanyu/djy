package com.djy.point.action;

import java.util.ArrayList;
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

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.point.vo.CstmConsumeVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.web.controller.WebController;


@Controller
@RequestMapping("/point/cstmConsume")
public class CstmConsumeController extends WebController {

	private static Logger logger = LoggerFactory.getLogger( CstmConsumeController.class );
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	
	@RequestMapping("/cstmConsumeDetail.do")
	public String cstmConsumeDetail(HttpSession session, Model m) {
		
		try {
			Integer userId = (Integer) session.getAttribute("userId");
			
			User user = userService.getById(userId);
			
			CoPartner coPartner = user.getCoPartner();
			
			m.addAttribute("coPartner", coPartner);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "/point/cstmConsume/cstmConsumeDetail";
	}
	
	/*获取积分详情列表*/
	@RequestMapping("/getConsumes.do")
	@ResponseBody
	public List<CstmConsumeVo> getConsumes(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
		Integer userId = (Integer) session.getAttribute("userId");
		
		User user = userService.getById(userId);
		
		CoPartner coPartner = user.getCoPartner();
		
		m.addAttribute("coPartner", coPartner);
		
		List<CstmConsumeVo> vos = new ArrayList<>();
		
		List<CstmConsumeVo> vos2 = new ArrayList<>();
		
		if (null != coPartner.getCoMode()) {
			if ( CoPartnerMode.perOrder.getId() == coPartner.getCoMode() ) {	// “每单结算”模式
				
				List<ConsumeOrder> consumeOrders = consumeOrderService.searchByCoPartnerId( coPartner.getId(), page);
				vos = CstmConsumeVo.transferListByConsumeOrders( consumeOrders );
				for(int i = 0;i<vos.size();i++){
					CstmConsumeVo consumeVo1 = vos.get(i);
					CstmConsumeVo consumeVo = CstmConsumeVo.getCsVo(consumeVo1);
					vos2.add(consumeVo);
				}
				
				
			} else if ( CoPartnerMode.sysDeposit.getId() == coPartner.getCoMode() ) {	// “平台预存”模式
				
				List<CoSysDepositLog> coSysDepositLogs = coSysDepositLogService.searchByCoPartnerId( coPartner.getId(),page );
				vos = CstmConsumeVo.transferListByCoSysDepositLogs( coSysDepositLogs );
				for(int i = 0;i<vos.size();i++){
					CstmConsumeVo consumeVo1 = vos.get(i);
					CstmConsumeVo consumeVo = CstmConsumeVo.getCsVo(consumeVo1);
					vos2.add(consumeVo);
				}
			}
		}
		
		return vos2;
		
	}
	
}
