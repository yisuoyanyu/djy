package com.djy.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.djy.admin.vo.CoSettleOrderVo;
import com.djy.co.model.CoSettleOrder;
import com.djy.co.service.CoSettleOrderService;
import com.djy.sys.service.CommonService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/admin/coSettleOrder/")
public class AdminCoSettleOrderController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoSettleOrderController.class);
	
	@Autowired
	private CoSettleOrderService coSettleOrderService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoSettleOrder")
	public CoSettleOrder preloadCoSettleOrder( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coSettleOrderService.get(id);
		}
		return new CoSettleOrder();
	}
	
	
	/**
	 * 商家结算订单信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSettleOrderRecord")
	public String coSettleOrderRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCoSettleOrder" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			CoSettleOrder coSettleOrder = preloadCoSettleOrder(id);
			
			m.addAttribute("coSettleOrder", coSettleOrder);
			
			return "/admin/coSettleOrder/coSettleOrderRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 订单列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSettleOrderList")
	public String coSysDepositOrderList( HttpSession session) {
		try {
			if( (!commonService.validateRes("ReadCoSettleOrder",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/coSettleOrder/coSettleOrderList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载订单列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param coPartnerName
	 * @param status
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSettleOrderListData")
	@ResponseBody
	public Object coSettleOrderListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			//获取当前登录用户
				
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("coPartnerName", coPartnerName);
			param.put("status", status);
			Date timeStart = null;
			Date timeEnd = null;
			
			if ( !StringUtil.isEmpty( insertTimeStart ) ) {
				Date date = DateUtil.toDate(insertTimeStart);
				timeStart = DateUtil.getFirstTimeOfDate(date);
			}
			
			if ( !StringUtil.isEmpty( insertTimeEnd ) ) {
				Date date = DateUtil.toDate(insertTimeEnd);
				timeEnd = DateUtil.getLastTimeOfDate(date);
			}
			
			param.put("timeStart", timeStart);
			param.put("timeEnd", timeEnd);
			List<CoSettleOrder> coSettleOrders = coSettleOrderService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoSettleOrderVo.transferList(coSettleOrders));
			
			return map;
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
