package com.djy.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.djy.admin.vo.CoSysDepositOrderVo;
import com.djy.admin.vo.SysUserVo;
import com.djy.co.enumtype.CoSysDepositOrderStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoPartnerService;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.co.service.CoSysDepositOrderService;
import com.djy.sys.model.SysUser;
import com.djy.sys.service.CommonService;
import com.djy.sys.service.SysUserService;
import com.djy.utils.enumtype.SnPrefix;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.SnGenerator;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;



@Controller
@RequestMapping("/admin/coSysDepositOrder/")
public class AdminCoSysDepositOrderController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoSysDepositOrderController.class);
	
	@Autowired
	private CoSysDepositOrderService coSysDepositOrderService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoSysDepositOrder")
	public CoSysDepositOrder preloadCoSysDepositOrder( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coSysDepositOrderService.get(id);
		}
		return new CoSysDepositOrder();
	}
	
	
	/**
	 * 平台预存订单信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSysDepositOrderRecord")
	public String coSysDepositOrderRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId, 
			Model m, HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCoSysDepositOrder" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			CoSysDepositOrder coSysDepositOrder = preloadCoSysDepositOrder(id);
			
			m.addAttribute("coSysDepositOrder", coSysDepositOrder);
			
			m.addAttribute("coPartner", coPartnerService.get(coPartnerId));
			
			return "/admin/coSysDepositOrder/coSysDepositOrderRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交平台预存单
	 * @param coPartnerAdTag
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoSysDepositOrder")
	@ResponseBody
	public Result submitCoSysDepositOrder( 
			@ModelAttribute("preloadCoSysDepositOrder") CoSysDepositOrder coSysDepositOrder,
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId, 
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCoSysDepositOrder",session)) 
					&& (!commonService.validateRes("EditCoSysDepositOrder",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			//获取当前登录用户
			SysUserVo sysUserVo = (SysUserVo) session.getAttribute(ConfigUtil.get("sys.session.loginUser"));
			SysUser sysUser = sysUserService.get(sysUserVo.getId());
			
			if ( !StringUtil.isEmpty( coPartnerId ) ) {
				CoPartner coPartner = coPartnerService.get(coPartnerId);
				
				// 新建预存订单
				coSysDepositOrder.setNo( SnGenerator.generate( SnPrefix.coSysDepositOrder.getValue() ) );
				coSysDepositOrder.setCoPartner( coPartner );
				coSysDepositOrder.setStatus( CoSysDepositOrderStatus.paySuccess.getId() );
				coSysDepositOrder.setInsertTime( new Date() );
				coSysDepositOrder.setSysUser( sysUser );
				coSysDepositOrder.setPayAmount(coSysDepositOrder.getAmount() * coPartner.getSysSettlePercent() / 100);
				coSysDepositOrderService.saveOrUpdate( coSysDepositOrder );
				
				// 更新平台预存额，记录预存额日志
				coSysDepositLogService.logCoSysDeposit( coSysDepositOrder );
				
			} else {
				return new Result(false, "合作商家不存在！");
			}
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 订单列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSysDepositOrderList")
	public String coSysDepositOrderList(HttpSession session) {
		
		try {
			if( (!commonService.validateRes("ReadCoSysDepositOrder",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/coSysDepositOrder/coSysDepositOrderList";
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
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSysDepositOrderListData")
	@ResponseBody
	public Object coSysDepositOrderListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
				
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
			param.put("coPartnerId", coPartnerId);
			
			List<CoSysDepositOrder> coSysDepositOrders = coSysDepositOrderService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoSysDepositOrderVo.transferList(coSysDepositOrders));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
