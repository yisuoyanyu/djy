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

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.bonus.model.BonusStats;
import com.djy.bonus.service.BonusDayStatsService;
import com.djy.bonus.service.BonusStatsService;
import com.djy.admin.vo.BonusDayStatsVo;
import com.djy.admin.vo.BonusStatsVo;
import com.djy.sys.model.SysEmpl;
import com.djy.sys.service.SysEmplService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/admin/bonusStats/")
public class AdminBonusStatsController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminBonusStatsController.class);
	
	@Autowired
	private BonusStatsService bonusStatsService;
	
	@Autowired
	private BonusDayStatsService bonusDayStatsService;
	
	@Autowired
	private SysEmplService sysEmplService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadBonusStats")
	public BonusStats preloadBonusStats( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return bonusStatsService.get(id);
		}
		return new BonusStats();
	}
	
	
	/**
	 * 提成统计信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/bonusStatsRecord")
	public String bonusStatsRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			BonusStats bonusStats = preloadBonusStats(id);
			
			m.addAttribute("bonusStats", bonusStats);
			
			return "/admin/bonusStats/bonusStatsRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提成统计列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/bonusStatsList")
	public String bonusStatsList(HttpSession session) {
		
		return "/admin/bonusStats/bonusStatsList";
		
	}
	
	/**
	 * 加载提成统计列表
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerName
	 * @param statsDate
	 * @param emplID
	 * @param session
	 * @return
	 */
	@RequestMapping("/bonusStatsListData")
	@ResponseBody
	public Object bonusStatsListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerName", required=false) String coPartnerName,
			@RequestParam(value="statsDate", required=false) Long statsDate,
			@RequestParam(value="emplID", required=false) String emplID,
			HttpSession session
		) {
		try {
			
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			
			Date timeStart = null;
			Date timeEnd = null;
			
			if (statsDate != null) {
				Date relStatsDate = new Date(statsDate);
				timeStart = DateUtil.getFirstTimeOfDate(relStatsDate);
				timeEnd = DateUtil.getLastTimeOfDate(relStatsDate);
			}
			
			param.put("timeStart", timeStart);
			param.put("timeEnd", timeEnd);
			
			param.put("coPartnerName", coPartnerName);
			param.put("emplID", emplID);
			
			List<BonusStats> bonusStatses = bonusStatsService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", BonusStatsVo.transferList(bonusStatses));
			
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		
	}
	
	
	/**
	 * 统计列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/bonusDayStatsList")
	public String bonusDayStatsList(HttpSession session) {
		
		return "/admin/bonusStats/bonusDayStatsList";
		
	}
	
	/**
	 * 加载统计列表
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param emplID
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/bonusDayStatsListData")
	@ResponseBody
	public Object bonusDayStatsListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="emplID", required=false) String emplID,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			
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
			
			if (!StringUtil.isBlank(emplID)) {
				SysEmpl sysEmpl = sysEmplService.getByEmplID(emplID);
				if (sysEmpl != null) {
					param.put("sysEmplId", sysEmpl.getId());
				}
			}
			
			List<BonusDayStatsBo> bonusDayStatsBos = bonusDayStatsService.searchAllBonusStats(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", BonusDayStatsVo.transferList(bonusDayStatsBos));
			
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		
	}
	
	
}
