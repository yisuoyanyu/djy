package com.djy.empl.action;

import java.util.Date;

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

import com.djy.sys.enumtype.SysEmplStatus;
import com.djy.sys.model.SysEmpl;
import com.djy.sys.service.SysEmplService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/empl/sysEmpl/")
public class EmplSysEmplController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(EmplSysEmplController.class);
	
	@Autowired
	private SysEmplService sysEmplService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 调用AdminSysEmplController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadSysEmpl")
	public SysEmpl preloadSysEmpl( @RequestParam(value = "id", required = false) Integer id) {
		SysEmpl sysEmpl = new SysEmpl();
		if ( id != null ) {
			sysEmpl = sysEmplService.get(id);
		}
		return sysEmpl;
	}
	
	
	/**
	 * 职员信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/sysEmplRecord")
	public String sysEmplRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m, HttpSession session) {
		try {
			
			SysEmpl sysEmpl = preloadSysEmpl(id);
			if (sysEmpl == null) {
				logger.error("职员不存在，请联系系统管理员！");
				return "";
			}
			
			m.addAttribute("sysEmpl", sysEmpl);
			
			m.addAttribute("sysEmplStatuses", SysEmplStatus.values());
			
			return "/empl/sysEmpl/sysEmplRecord";
		} catch (Exception e) {
			logger.error("", e);
			return "";
		}
		
	}
	
	
	/**
	 * 提交职员信息
	 * @param sysEmpl
	 * @param emplID
	 * @param initPassword
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitSysEmpl")
	@ResponseBody
	public Result submitSysEmpl( 
			@ModelAttribute("preloadSysEmpl") SysEmpl sysEmpl, 
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value = "userMobile", required = false) String userMobile,
			@RequestParam(value="emplID", required=false) String emplID, 
			@RequestParam(value="initPassword", required=false) String initPassword, 
			HttpSession session) {
		try {
			
			User user = userService.getUserByMobile(userMobile);
			if (user != null) {
				//判断通过该手机号查询到的会员是否已匹配过其它合作商家
				if (user.getSysEmpl() != null && user.getSysEmpl().getId() != sysEmpl.getId()) {
					return new Result(false, "关联会员手机对应会员已存在相匹配的职员，请重新输入关联会员手机！");
				}
			} else {
				return new Result(false, "不存在该手机号对应会员，请重新输入关联会员手机！");
			}
			
			boolean isNew = false;
			if ( sysEmpl.getId() == null ) isNew = true;
			
			// 检查工号是否被占用
			if ( emplID != null ) {
				SysEmpl tmpSysEmpl = sysEmplService.getByEmplID( emplID );
				if ( tmpSysEmpl != null ) {
					if ( isNew ) {
						return new Result(false, "工号已被占用！");
					} else {
						if ( !tmpSysEmpl.getId().equals( sysEmpl.getId() ) )
							return new Result(false, "工号已被占用！");
					}
				}
			}
			
			if ( isNew ) {
				sysEmpl.setPassword("");
				sysEmpl.setInsertTime( new Date() );
				sysEmplService.save( sysEmpl );
				
				if ( !"".equals( initPassword ) ) {
					String password = sysEmplService.encodePassword(sysEmpl, initPassword);
					sysEmpl.setPassword( password );
				}
				
			} else {
				
				if ( !"".equals( initPassword ) ) {
					String password = sysEmplService.encodePassword(sysEmpl, initPassword);
					sysEmpl.setPassword( password );
				}
				
			}
			
			sysEmpl.setUser(userService.get(userId));
			
			sysEmplService.saveOrUpdate(sysEmpl);
			
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
}
