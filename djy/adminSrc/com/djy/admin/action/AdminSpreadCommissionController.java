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

import com.djy.admin.vo.SpreadCommissionVo;
import com.djy.admin.vo.SysUserVo;
import com.djy.spread.model.SpreadCommission;
import com.djy.spread.service.SpreadCommissionService;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/admin/spreadCommission/")
public class AdminSpreadCommissionController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminSpreadCommissionController.class);
	
	@Autowired
	private SpreadCommissionService spreadCommissionService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadSpreadCommission")
	public SpreadCommission preloadSpreadCommission( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return spreadCommissionService.get(id);
		}
		return new SpreadCommission();
	}
	
	
	/**
	 * 推广佣金详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/spreadCommissionRecord")
	public String spreadCommissionRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			SpreadCommission spreadCommission = preloadSpreadCommission(id);
			
			m.addAttribute("spreadCommission", spreadCommission);
			
			return "/admin/spreadCommission/spreadCommissionRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 推广佣金列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/spreadCommissionList")
	public String spreadCommissionList( HttpSession session) {
		try {
			
			return "/admin/spreadCommission/spreadCommissionList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载推广佣金列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param status
	 * @param realName
	 * @param mobile
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/spreadCommissionListData")
	@ResponseBody
	public Object spreadCommissionListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="spreadPromoterId", required=false) Integer spreadPromoterId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			//获取当前登录用户
				
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("status", status);
			param.put("realName", realName);
			param.put("mobile", mobile);
			param.put("spreadPromoterId", spreadPromoterId);
			
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
			List<SpreadCommission> spreadCommissions = spreadCommissionService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", SpreadCommissionVo.transferList(spreadCommissions));
			
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	
	/**
	 * 推广佣金统计
	 * @param searchText
	 * @param status
	 * @param realName
	 * @param mobile
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param spreadPromoterId
	 * @param session
	 * @return
	 */
	@RequestMapping("/countCommissionTotalAmount")
	@ResponseBody
	public Result countCommissionTotalAmount(
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="spreadPromoterId", required=false) Integer spreadPromoterId,
			HttpSession session
		) {
		try {
			
			//获取当前登录用户
			SysUserVo sysUserVo = (SysUserVo) session.getAttribute(ConfigUtil.get("sys.session.loginUser"));
			
			if(sysUserVo != null){
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("searchText", searchText);
				param.put("status", status);
				param.put("realName", realName);
				param.put("mobile", mobile);
				param.put("spreadPromoterId", spreadPromoterId);
				
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

				
				Map<String, Object> totalAmountMap = new HashMap<String, Object>();
				
				double totalAmount = spreadCommissionService.countCommissionTotalAmount(param);
				
				totalAmountMap.put("totalAmount", totalAmount);
				
				return new Result(true, totalAmountMap);
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
