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

import com.djy.admin.vo.UserVo;
import com.djy.sys.service.CommonService;
import com.djy.user.enumtype.UserStatus;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/user/")
public class AdminUserController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 调用AgentUserController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadUser")
	public User preloadUser( @RequestParam(value = "id", required = false) Integer id ) {
		User user = new User();
		if ( id != null ) {
			return userService.get(id);
		}
		return user;
	}
	
	/**
	 * 用户信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/userRecord")
	public String userRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m ,HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadUser" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			if(id != null){
				m.addAttribute("statusName", UserStatus.fromId(preloadUser(id).getStatus()).getValue());
			}
			m.addAttribute("user", preloadUser(id));
			return "/admin/user/userRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	/**
	 * 用户信息列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadUser" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			return "/admin/user/userList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载用户信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param username
	 * @param mobile
	 * @param searchText
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/userListData")
	@ResponseBody
	public Object userListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("username", username);
			param.put("mobile", mobile);
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
			List<User> users = userService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", UserVo.transferList(users));
			
			return map;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 通过手机号查找用户
	 * @param mobile
	 * @param spreadPromoterId
	 * @return
	 */
	@RequestMapping("/getUserByMobile")
	@ResponseBody
	public Result getUserByMobile(
			@RequestParam(value="mobile", required=false) String mobile ,
			@RequestParam(value="spreadPromoterId", required=false) Integer spreadPromoterId) {
		
		try {
			if (!StringUtil.isBlank(mobile)) {
				Map<String, Object> map = new HashMap<String, Object>();
				User user = userService.getUserByMobile(mobile);
				if (user != null) {
					
					//判断通过该手机号查询到的会员是否已匹配过其他推广人
					if ( user.getSpreadPromoter() != null && user.getSpreadPromoter().getId() != spreadPromoterId ) {
						return new Result(false, "您输入的关联会员手机已被使用，请重新输入关联会员手机！");
					}
					
					map.put("userId", user.getId());
					map.put("nickName", user.getWechatUser().getNickname());
					return new Result(true, map);
				} else {
					return new Result(false, "不存在该手机号对应会员，请重新输入关联会员手机！");
				}
			} else {
				return new Result(false, "");
			}
			
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "程序错误，请联系管理员！");
		}
	}
	
	
}
