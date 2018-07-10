package com.djy.partner.action;

import java.text.DecimalFormat;
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

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerShift;
import com.djy.co.service.CoPartnerService;
import com.djy.co.service.CoPartnerShiftService;
import com.djy.co.service.CoSettleOrderService;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.co.service.CoSysDepositOrderService;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.partner.vo.CoPartnerShiftVo;
import com.djy.partner.vo.CoPartnerVo;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/partner/coPartnerShift/")
public class PartnerCoPartnerShiftController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCoPartnerShiftController.class);
	
	@Autowired
	private CoPartnerShiftService coPartnerShiftService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	@Autowired
	private CoSysDepositOrderService coSysDepositOrderService;
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	@Autowired
	private CoSettleOrderService coSettleOrderService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartnerShift")
	public CoPartnerShift preloadCoPartnerShift( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coPartnerShiftService.get(id);
		}
		return new CoPartnerShift();
	}
	
	
	/**
	 * 交接班信息详情
	 * @param id
	 * @param m
	 * @return
	 */
	@RequestMapping("/coPartnerShiftRecord")
	public String coPartnerShiftRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m) {
		try {
			
			CoPartnerShift coPartnerShift = preloadCoPartnerShift(id);
			
			m.addAttribute("coPartnerShift", coPartnerShift);
			m.addAttribute("coPartner", coPartnerShift.getCoPartner());
			
			if (coPartnerShift.getCoPartner().getCoMode() == CoPartnerMode.sysDeposit.getId()) {
				m.addAttribute("sysDeposit", CoPartnerMode.sysDeposit.getId());
			}
			
			return "/partner/coPartnerShift/coPartnerShiftRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 * 交接班列表
	 * @param session
	 * @param m
	 * @return
	 */
	@RequestMapping("/coPartnerShiftList")
	public String coPartnerShiftList(HttpSession session,Model m) {
		
		CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
		
		CoPartner coPartner = coPartnerService.get(coPartnerVo.getId());
		
		//平台预存
		if (coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId()) {
			m.addAttribute("sysDeposit", CoPartnerMode.sysDeposit.getId());
		}
		
		return "/partner/coPartnerShift/coPartnerShiftList";
		
	}
	
	/**
	 * 加载交接班列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @param m
	 * @return
	 */
	@RequestMapping("/coPartnerShiftListData")
	@ResponseBody
	public Object coPartnerShiftListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session,Model m
		) {
		try {
			
			CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
			
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("coPartnerId", coPartnerVo.getId());
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
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
			
			List<CoPartnerShift> coPartnerShifts = coPartnerShiftService.search(pb, param);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerShiftVo.transferList(coPartnerShifts));
		
			return map;
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 获取合作商家最近一次交接班时间
	 * @param handoverTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/getLastCoPartnerShiftHandoverTime")
	@ResponseBody
	public Object getLastCoPartnerShiftHandoverTime(
			@RequestParam(value="handoverTimeEnd", required=false) String handoverTimeEnd,
			HttpSession session
		) {
		try {
			
			CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("coPartnerId", coPartnerVo.getId());
			
			if (!StringUtil.isBlank(handoverTimeEnd)) {
				String handoverTimeStart = coPartnerShiftService.getLastCoPartnerShiftHandoverTime(param);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("handoverTimeStart",handoverTimeStart);
			
				return map;
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return null;
		}
		return null;
		
	}
	
	
	
	/**
	 * 交接班
	 * @param session
	 * @param m
	 * @return
	 */
	@RequestMapping("/addCoPartnerShift")
	@ResponseBody
	public Result addCoPartnerShift(HttpSession session,Model m
		) {
		try {
			
			CoPartnerVo coPartnerVo = (CoPartnerVo) session.getAttribute(ConfigUtil.get("sys.session.loginPartner"));
			
			CoPartner coPartner = coPartnerService.get(coPartnerVo.getId());
			
			Date now = new Date();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("coPartnerId", coPartnerVo.getId());
			
			String startTime = coPartnerShiftService.getLastCoPartnerShiftHandoverTime(param);
			String endTime = DateUtil.formatDate(now, "yyyy-MM-dd HH:mm:ss");
			
			if (!StringUtil.isBlank(endTime)) {
				
				Date timeStart = null;
				Date timeEnd = null;
				
				if ( !StringUtil.isEmpty( startTime ) ) {
					Date date = DateUtil.toDate(startTime);
					timeStart = DateUtil.getFirstTimeOfDate(date);
				}
				
				if ( !StringUtil.isEmpty( endTime ) ) {
					Date date = DateUtil.toDate(endTime);
					timeEnd = DateUtil.getLastTimeOfDate(date);
				}
				
				param.put("timeStart", timeStart);
				param.put("timeEnd", timeEnd);
				
				CoPartnerShift coPartnerShift = new CoPartnerShift();
				
				//获取这段交接时间内的会员消费总额
				double totalCstmConsume = consumeOrderService.getConsumeOrderAmount(param);
				//获取这段交接时间内的会员实付总额
				double totalCstmPay = consumeOrderService.getConsumeOrderPayAmount(param);
				//获取这段交接时间内的“每单结算”模式下，会员消费平台结算总额
				double totalCstmSettle = coSettleOrderService.getCoSettleOrderAmount(param);
				
				//保留两位小数
				DecimalFormat df = new DecimalFormat("#.00"); 
				
				//平台预存
				if (coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId()) {
					
					//获取这段交接时间内的平台预存支付总额
					double totalSysDepositPay = coSysDepositOrderService.getCoSysDepositPayOrderAmount(param);
					//获取这段交接时间内的平台预存总额
					double totalSysDeposit = coSysDepositOrderService.getCoSysDepositOrderAmount(param);
					
					//获取这段交接时间内的期初平台预存额
					double startSysDeposit = 0d;
					if ( !StringUtil.isBlank(startTime)) {
						startSysDeposit = coSysDepositLogService.getCoSysDepositLogStartSysDeposit(param);
					}
					
					//获取这段交接时间内的期末平台预存额
					double endSysDeposit = coSysDepositLogService.getCoSysDepositLogEndSysDeposit(param);
					
					coPartnerShift.setTotalSysDepositPay(Double.parseDouble(df.format(totalSysDepositPay)));
					coPartnerShift.setTotalSysDeposit(Double.parseDouble(df.format(totalSysDeposit)));
					
					coPartnerShift.setStartSysDeposit(Double.parseDouble(df.format(startSysDeposit)));
					coPartnerShift.setEndSysDeposit(Double.parseDouble(df.format(endSysDeposit)));
					
				}
				
				coPartnerShift.setCoPartner(coPartner);
				coPartnerShift.setTotalCstmConsume(Double.parseDouble(df.format(totalCstmConsume)));
				coPartnerShift.setTotalCstmPay(Double.parseDouble(df.format(totalCstmPay)));
				coPartnerShift.setTotalCstmSettle(Double.parseDouble(df.format(totalCstmSettle)));
				
				Date relHandoverTimeStart = DateUtil.toDateTime(startTime);
				coPartnerShift.setStartTime(relHandoverTimeStart);
				
				Date relHandoverTimeEnd = DateUtil.toDateTime(endTime);
				coPartnerShift.setEndTime(relHandoverTimeEnd);
				
				coPartnerShiftService.save(coPartnerShift);
				return new Result(true, "交接班成功");
			} 
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
		return new Result(false, "交接班失败！");
		
	}
	
	
}
