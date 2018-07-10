package com.djy.copartner.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.djy.bonus.bo.BonusDayStatsBo;
import com.djy.bonus.model.BonusStats;
import com.djy.bonus.service.BonusDayStatsService;
import com.djy.bonus.service.BonusStatsService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.controller.WebController;



@Controller
@RequestMapping("/copartner/bouns")
public class BonusStatsController extends WebController{

	private static Logger logger = LoggerFactory.getLogger(BonusStatsController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BonusStatsService bonusStatsService;
	
	@Autowired
	private BonusDayStatsService bonusDayStatsService;
	
	
	
	/**
	 * 获取每天提成列表
	 * 
	 * @param page
	 * @param session
	 * @param m
	 * @return
	 */
	@RequestMapping("/bounsList.do")
	public String bounsList(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m){
          Integer userId = (Integer) session.getAttribute("userId");
  		  User user = userService.getById(userId);
  		  
	  		int pageSize = 10;                              //每页显示记录数
			
			Date statrtTime = DateUtil.getFirstTimeOfDate(user.getSysEmpl().getInsertTime());
			long totalItems = DateUtil.diffDay(statrtTime, new Date());//总记录数；总天数减一表示总共有多少的数据
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
			
			Date today = new Date();
			Date startFindDate = DateUtil.addDay(DateUtil.getLastTimeOfDate(today), -startNum);//开始查询时间
			Date endFindDate = DateUtil.addDay(DateUtil.getFirstTimeOfDate(today), -endNum);//结束查询时间
			
			
			
          try {
        	  double totalCstmConsume = bonusStatsService.getTotalCstmConsumeByUser(user);//总的营业额
        	  double totalBonus = bonusStatsService.getTotalBonusByUser(user);

              m.addAttribute("totalCstmConsume", totalCstmConsume);
              m.addAttribute("totalBonus", totalBonus);
              
              List<BonusDayStatsBo> bonusStats = bonusDayStatsService.getDayTotalByUser(user, endFindDate, startFindDate);
              
      		m.addAttribute("bonusStats", bonusStats);
    		
    		m.addAttribute("page", page);
    		m.addAttribute("totalPages", totalPages);
    		m.addAttribute("lastPage", lastPage);
    		m.addAttribute("nextPage", nextPage);
    		m.addAttribute("startShowPage", startShowPage);
    		m.addAttribute("endShowPage", endShowPage);
    		
    		String nickName = user.getWechatUser().getNickname();
    		m.addAttribute("nickName", nickName);
          
          } catch (Exception e) {
			System.out.println(e.getMessage());
		}
		  return "/copartner/bouns/bounsList";
	}
	
	
	/**
	 * 获取每月提成列表
	 * 
	 * @param page
	 * @param session
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bounsMonthList.do")
	public String bounsMonthList(
			@RequestParam(value="page", required=false) int page,
			HttpSession session, Model m) throws Exception{
          Integer userId = (Integer) session.getAttribute("userId");
  		  User user = userService.getById(userId);
  		int pageSize = 10;       
  		Date statrtTime = DateUtil.getFirstTimeOfDate(user.getSysEmpl().getInsertTime());
		int monthes = DateUtil.diffMonth(statrtTime, new Date());//月份总记录数
		   
		int startNum = 0;
		int endNum = 0;
		  
		int totalPages = 0;  //总页数
		
		startNum = (page - 1)*10+1;
		
		if (monthes%pageSize == 0) {
			totalPages = ((int)monthes/pageSize);
			endNum = page*10;
		}else {
			totalPages = ((int)monthes/pageSize)+1;
			if (page == totalPages) {
				endNum = (page - 1)*10 + (int)(monthes%pageSize);
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
			
			if (page + 7 < monthes) {
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
		
		  double totalCstmConsume = bonusStatsService.getTotalCstmConsumeByUser(user);//总的营业额
    	  double totalBonus = bonusStatsService.getTotalBonusByUser(user);

          m.addAttribute("totalCstmConsume", totalCstmConsume);
          m.addAttribute("totalBonus", totalBonus);
		
		Date today = new Date();
		Date startFindDate = DateUtil.addMonth(DateUtil.getLastTimeOfDate(today), -(startNum-1));//开始查询时间
		startFindDate = DateUtil.getLastTimeOfMonth(startFindDate);
		Date endFindDate = DateUtil.addMonth(DateUtil.getFirstTimeOfDate(today), -endNum);//结束查询时间
  		endFindDate = DateUtil.getFirstTimeOfMonth(endFindDate);
		List<BonusDayStatsBo> bonusStats = bonusDayStatsService.getMonthTotalByUser(user, endFindDate, startFindDate);
		m.addAttribute("bonusStats", bonusStats);
		
		m.addAttribute("page", page);
		m.addAttribute("totalPages", totalPages);
		m.addAttribute("lastPage", lastPage);
		m.addAttribute("nextPage", nextPage);
		m.addAttribute("startShowPage", startShowPage);
		m.addAttribute("endShowPage", endShowPage);
		
		String nickName = user.getWechatUser().getNickname();
		m.addAttribute("nickName", nickName);
		
		return "/copartner/bouns/bounsMonthList";
	}
	
	
	
	/**
	 * 获取每天提成详情列表
	 * 
	 * @param getdate
	 * @param session
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/accBounsList.do")
	public String accBounsList(
			@RequestParam(value="getdate", required=false) String getdate,
			HttpSession session, Model m) throws Exception{
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		
		//SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		 SimpleDateFormat sf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		Date nowDate = sf.parse(getdate);
		
		List<BonusStats> bonusStats = bonusStatsService.getDayAccList(user, nowDate);
		m.addAttribute("bonusStats", bonusStats);
		String nickName = user.getWechatUser().getNickname();
		m.addAttribute("nickName", nickName);
		 return "/copartner/bouns/accBounsList";
		
	}
	
	
	/**
	 * 获取每月提成详情列表
	 * 
	 * @param getdate
	 * @param session
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/accMonBounsList.do")
	public String accMonBounsList(
			@RequestParam(value="getdate", required=false) String getdate,
			HttpSession session, Model m) throws Exception{
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		 
		
		SimpleDateFormat sf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		Date nowDate = sf.parse(getdate);
		
		Date monBagin = DateUtil.getFirstTimeOfMonth(nowDate);//获取本月的开始时间
		Date monEnd = DateUtil.getLastTimeOfMonth(nowDate);
		
		 List<BonusDayStatsBo> bonusStats = bonusDayStatsService.getDayTotalByUser(user,monBagin,monEnd);
		
		m.addAttribute("bonusStats", bonusStats);
		String nickName = user.getWechatUser().getNickname();
		m.addAttribute("nickName", nickName);
		return "/copartner/bouns/accMonBounsList";
		
	}
	
}
