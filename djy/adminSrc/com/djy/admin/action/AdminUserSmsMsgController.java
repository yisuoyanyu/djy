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

import com.djy.admin.vo.UserSmsMsgVo;
import com.djy.sys.service.CommonService;
import com.djy.user.model.UserSmsMsg;
import com.djy.user.service.UserSmsMsgService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;

@Controller
@RequestMapping("/admin/userSmsMsg/")
public class AdminUserSmsMsgController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminUserSmsMsgController.class);
	
	@Autowired
	private UserSmsMsgService userSmsMsgService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadUserSmsMsg")
	public UserSmsMsg preloadUserSmsMsg( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return userSmsMsgService.get(id);
		}
		return new UserSmsMsg();
	}
	
	
	/**
	 * 用户短信消息
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/userSmsMsgRecord")
	public String userSmsMsgRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadUserSmsMsg" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			UserSmsMsg userSmsMsg = preloadUserSmsMsg(id);
			
			m.addAttribute("userSmsMsg", userSmsMsg);
			
			return "/admin/userSmsMsg/userSmsMsgRecord";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	
	//------------------------------------------------------------------------------
	
	
	/**
	 *用户短信消息列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/userSmsMsgList")
	public String userSmsMsgList(HttpSession session) {
		
		try {
			
			if( !commonService.validateRes("ReadUserSmsMsg" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/userSmsMsg/userSmsMsgList";
			
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载用户短信消息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param userId
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/userSmsMsgListData")
	@ResponseBody
	public Object userSmsMsgListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="userId", required=false) Integer userId,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("userId", userId);
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
			List<UserSmsMsg> userSmsMsgs = userSmsMsgService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", UserSmsMsgVo.transferList(userSmsMsgs));
			
			return map;
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
