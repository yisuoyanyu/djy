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

import com.djy.admin.vo.SysEmplVo;
import com.djy.sys.enumtype.SysEmplStatus;
import com.djy.sys.model.SysEmpl;
import com.djy.sys.service.SysEmplService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/sysEmpl/")
public class AdminSysEmplController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminSysEmplController.class);
	
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
			
			return "/admin/sysEmpl/sysEmplRecord";
		} catch (Exception e) {
			logger.error("", e);
			return "";
		}
		
	}
	
	
	/**
	 * 提交职员信息
	 * @param sysEmpl
	 * @param userId
	 * @param userMobile
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
	
	
	/**
	 * 删除职员
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delSysEmpl")
	@ResponseBody
	public Result delSysEmpl( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			
			int num = sysEmplService.delSysEmpl(ids);
			if(num > 0){
				return new Result(true, "成功删除选中职员！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 禁用职员
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/freezeSysEmpl")
	@ResponseBody
	public Result freezeSysEmpl( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		try {
			
			int num = sysEmplService.freezeSysEmpl(ids);
			if(num > 0){
				return new Result(true, "成功禁用选中职员！");
			}else{
				return new Result(false, "禁用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
		
	}
	
	
	/**
	 * 启用职员
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/normalSysEmpl")
	@ResponseBody
	public Result normalSysEmpl( @RequestParam(value="ids[]") Integer[] ids  ,HttpSession session) {
		
		try {
			
			int num = sysEmplService.normalSysEmpl(ids);
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
	 * 职员列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/sysEmplList")
	public String sysEmplList() {
		
		return "/admin/sysEmpl/sysEmplList";
		
	}
	
	/**
	 *  加载职员列表信息
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
	 * @return
	 */
	@RequestMapping("/sysEmplListData")
	@ResponseBody
	public Object sysEmplListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="emplID", required=false) String emplID,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd
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
			List<SysEmpl> sysEmpls = sysEmplService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", SysEmplVo.transferList(sysEmpls));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 通过手机号查找职员
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/getSysEmplByMobile")
	@ResponseBody
	public Result getSysEmplByMobile(
			@RequestParam(value="mobile", required=false) String mobile) {
		
		try {
			if (!StringUtil.isBlank(mobile)) {
				Map<String, Object> map = new HashMap<String, Object>();
				SysEmpl sysEmpl = sysEmplService.getSysEmplByMobile(mobile);
				if (sysEmpl != null) {
					map.put("sysEmplId", sysEmpl.getId());
					return new Result(true, map);
				} else {
					return new Result(false, "不存在该手机号对应职员，请重新输入所属职员手机！");
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
