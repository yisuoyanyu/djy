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

import com.djy.co.enumtype.CoPartnerEmplStatus;
import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.service.CoPartnerEmplService;
import com.djy.co.service.CoPartnerService;
import com.djy.admin.vo.CoPartnerEmplVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/coPartnerEmpl/")
public class AdminCoPartnerEmplController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoPartnerEmplController.class);
	
	@Autowired
	private CoPartnerEmplService coPartnerEmplService;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartnerEmpl")
	public CoPartnerEmpl preloadCoPartnerEmpl( @RequestParam(value = "id", required = false) Integer id) {
		CoPartnerEmpl coPartnerEmpl = new CoPartnerEmpl();
		if ( id != null ) {
			coPartnerEmpl = coPartnerEmplService.get(id);
		}
		return coPartnerEmpl;
	}
	
	/**
	 * 合作商家员工详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerEmplRecord")
	public String coPartnerEmplRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId,
			Model m, HttpSession session) {
		try {
			
			CoPartnerEmpl coPartnerEmpl = preloadCoPartnerEmpl(id);
			if (coPartnerEmpl == null) {
				logger.error("员工不存在，请联系系统管理员！");
				return "";
			}
			
			m.addAttribute("coPartnerEmpl", coPartnerEmpl);
			if (coPartnerId != null) {
				m.addAttribute("coPartner", coPartnerService.get(coPartnerId));
			} else {
				m.addAttribute("coPartner", coPartnerService.get(coPartnerEmpl.getCoPartner().getId()));
			}
			
			m.addAttribute("coPartnerEmplStatus", CoPartnerEmplStatus.values());
			m.addAttribute("sysDepositMode", CoPartnerMode.sysDeposit.getId());
			
			return "/admin/coPartnerEmpl/coPartnerEmplRecord";
		} catch (Exception e) {
			logger.error("", e);
			return "";
		}
		
	}
	
	
	/**
	 * 提交合作商家员工信息
	 * @param coPartnerEmpl
	 * @param userId
	 * @param userMobile
	 * @param emplID
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoPartnerEmpl")
	@ResponseBody
	public Result submitCoPartnerEmpl( 
			@ModelAttribute("preloadCoPartnerEmpl") CoPartnerEmpl coPartnerEmpl, 
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "userMobile", required = false) String userMobile,
			@RequestParam(value="emplID", required=false) String emplID, 
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			HttpSession session) {
		try {
			
			User user = userService.getUserByMobile(userMobile);
			if (user != null) {
				//判断该手机号查询到的会员是否已被匹配过
				if (user.getSysEmpl() != null || user.getCoPartner() != null 
						|| (user.getCoPartnerEmpl() != null && user.getCoPartnerEmpl().getId() != coPartnerEmpl.getId())) {
					return new Result(false, "您输入的会员手机已被使用，请重新输入会员手机！");
				}
			} else {
				return new Result(false, "您输入的会员手机号错误，请重新输入会员手机！");
			}
			
			boolean isNew = false;
			if ( coPartnerEmpl.getId() == null ) isNew = true;
			
			// 检查工号是否被占用
			if ( emplID != null ) {
				CoPartnerEmpl tmpCoPartnerEmpl = coPartnerEmplService.getByEmplID( emplID );
				if ( tmpCoPartnerEmpl != null ) {
					if ( isNew ) {
						return new Result(false, "工号已被占用！");
					} else {
						if ( !tmpCoPartnerEmpl.getId().equals( coPartnerEmpl.getId() ) )
							return new Result(false, "工号已被占用！");
					}
				}
			}
			
			if ( isNew ) {
				coPartnerEmpl.setInsertTime( new Date() );
				coPartnerEmpl.setCoPartner(coPartnerService.get(coPartnerId));
			} else {
				coPartnerEmpl.setCoPartner(coPartnerEmpl.getCoPartner());
			}
			
			coPartnerEmpl.setStatus(coPartnerEmpl.getStatus());
			coPartnerEmpl.setUser(userService.get(userId));
			
			coPartnerEmplService.saveOrUpdate(coPartnerEmpl);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
	/**
	 * 删除合作商家员工
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCoPartnerEmpl")
	@ResponseBody
	public Result delCoPartnerEmpl( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			
			int num = coPartnerEmplService.delCoPartnerEmpl(ids);
			if(num > 0){
				return new Result(true, "成功删除选中员工！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 禁用合作商家员工
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/freezeCoPartnerEmpl")
	@ResponseBody
	public Result freezeCoPartnerEmpl( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		try {
			
			int num = coPartnerEmplService.freezeCoPartnerEmpl(ids);
			if(num > 0){
				return new Result(true, "成功禁用选中员工！");
			}else{
				return new Result(false, "禁用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
		
	}
	
	
	/**
	 * 启用合作商家员工
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/normalCoPartnerEmpl")
	@ResponseBody
	public Result normalCoPartnerEmpl( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		
		try {
			
			int num = coPartnerEmplService.normalCoPartnerEmpl(ids);
			if(num > 0){
				return new Result(true, "成功启用选中员工！");
			}else{
				return new Result(false, "启用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	/**
	 * 加载合作商家员工列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param emplID
	 * @param realName
	 * @param mobile
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @return
	 */
	@RequestMapping("/coPartnerEmplListData")
	@ResponseBody
	public Object coPartnerEmplListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="emplID", required=false) String emplID,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("emplID", emplID);
			param.put("realName", realName);
			param.put("mobile", mobile);
			
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
			List<CoPartnerEmpl> coPartnerEmpls = coPartnerEmplService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerEmplVo.transferList(coPartnerEmpls));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
