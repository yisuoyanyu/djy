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

import com.djy.admin.vo.CoSysDepositLogVo;
import com.djy.admin.vo.SysUserVo;
import com.djy.co.enumtype.CoSysDepositLogIncomeExpense;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.sys.service.CommonService;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/admin/coSysDepositLog/")
public class AdminCoSysDepositLogController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoSysDepositLogController.class);
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 调用AdminViOrderLogController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoSysDepositLog")
	public CoSysDepositLog preloadCoSysDepositLog( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coSysDepositLogService.get(id);
		}
		return new CoSysDepositLog();
	}
	
	//------------------------------------------------------------------------------
	
	/**
	 * 平台预存款列表
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coSysDepositLogList")
	public String coSysDepositLogList(Model m, HttpSession session) {
		
		try {
			
			if( !commonService.validateRes("ReadCoSysDepositLog" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("coSysDepositLogIncomeExpense", CoSysDepositLogIncomeExpense.values());
			
			return "/admin/coSysDepositLog/coSysDepositLogList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 订单日志列表信息加载
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param coPartnerName
	 * @param incomeExpense
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @param coSysDepositOrderId
	 * @return
	 */
	@RequestMapping("/coSysDepositLogListData")
	@ResponseBody
	public Object coSysDepositLogListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="incomeExpense", required=false) Integer incomeExpense,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			@RequestParam(value="coSysDepositOrderId", required=false) Integer coSysDepositOrderId
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("coPartnerName", coPartnerName);
			param.put("incomeExpense", incomeExpense);
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
			param.put("coSysDepositOrderId", coSysDepositOrderId);
			
			List<CoSysDepositLog> coSysDepositLogs = coSysDepositLogService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoSysDepositLogVo.transferList(coSysDepositLogs));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 预存款明细统计
	 * @param searchText
	 * @param coPartnerName
	 * @param incomeExpense
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/countCoSysDeposit")
	@ResponseBody
	public Result countCoSysDeposit(
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="incomeExpense", required=false) Integer incomeExpense,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			HttpSession session
		) {
		try {
			
			//获取当前登录用户
			SysUserVo sysUserVo = (SysUserVo) session.getAttribute(ConfigUtil.get("sys.session.loginUser"));
			
			if(sysUserVo != null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("searchText", searchText);
				param.put("coPartnerName", coPartnerName);
				param.put("incomeExpense", incomeExpense);
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
				
				Map<String, Object> totalAmount = new HashMap<String, Object>();
				
				if ( StringUtil.isBlank(incomeExpense)) {
					//计算预存款支出总金额
					param.put("incomeExpense", CoSysDepositLogIncomeExpense.expense.getId());
					double expenseTotalAmount = coSysDepositLogService.search(param);
					totalAmount.put("expenseTotalAmount", expenseTotalAmount);
					
					////计算预存款收入总金额
					param.put("incomeExpense", CoSysDepositLogIncomeExpense.income.getId());
					double incomeTotalAmount = coSysDepositLogService.search(param);
					totalAmount.put("incomeTotalAmount", incomeTotalAmount);
					
				} else {
					if (incomeExpense ==  CoSysDepositLogIncomeExpense.expense.getId()) {
						double expenseTotalAmount = coSysDepositLogService.search(param);
						totalAmount.put("expenseTotalAmount", expenseTotalAmount);
					} else if (incomeExpense ==  CoSysDepositLogIncomeExpense.income.getId()) {
						double incomeTotalAmount = coSysDepositLogService.search(param);
						totalAmount.put("incomeTotalAmount", incomeTotalAmount);
					} 
				}
				
				return new Result(true, totalAmount);
			}else{
				logger.error("用户登录过期，请重新登录！");
				return new Result(false, "");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, e.toString());
		}
		
	}
	
}
