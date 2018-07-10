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

import com.djy.admin.vo.SysUserVo;
import com.djy.sys.enumtype.SysUserStatus;
import com.djy.sys.model.SysRole;
import com.djy.sys.model.SysUser;
import com.djy.sys.service.CommonService;
import com.djy.sys.service.SysRoleService;
import com.djy.sys.service.SysUserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/admin/sysUser/")
public class AdminSysUserController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminSysUserController.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 调用AdminSysUserController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadSysUser")
	public SysUser preloadSysUser( @RequestParam(value = "id", required = false) Integer id ) {
		SysUser sysUser = new SysUser();
		if ( id != null ) {
			sysUser = sysUserService.get(id);
		}
		return sysUser;
	}
	
	/**
	 * 系统用户信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/sysUserRecord")
	public String sysUserRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m ,HttpSession session) {
		try {
			if(!commonService.validateRes("ReadSysUser" ,session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";	
			}
			
			m.addAttribute("sysUser", preloadSysUser(id));
			
			List<SysRole> sysRoles = sysRoleService.findStaffRoles();
			m.addAttribute("sysRoles", sysRoles);
			
			return "/admin/sysUser/sysUserRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交信息用户信息
	 * @param id
	 * @param username
	 * @param realName
	 * @param password
	 * @param mobile
	 * @param sysRoleId
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitSysUser")
	@ResponseBody
	public Result submitSysUser( 
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="realName", required=false) String realName,
			@RequestParam(value="password", required=false) String password, 
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="sysRoleId", required=false) Integer sysRoleId,
			HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddSysUser",session)) 
				&& (!commonService.validateRes("EditSysUser",session)) ){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			//使用@ModelAttribute("preloadSysUser")传参时，密码会传递为空值，提交后用户密码会变为空，因此不采用@ModelAttribute("preloadSysUser")传参
			if(id == null){//新增
				SysUser  user = sysUserService.getByUsername(username);
				
				if(user == null){
					SysUser sysUser = new SysUser();
					sysUser.setUsername(username);
					sysUser.setPassword(password);
					sysUser.setRealName(realName);
					sysUser.setMobile(mobile);
					sysUser.setInsertTime(new Date());
					sysUser.setStatus(SysUserStatus.normal.getId());
					SysRole sysRole = null;
					if (sysRoleId != null) {
						sysRole = sysRoleService.get(sysRoleId);
					}
					sysUser.setSysRole(sysRole);
					sysUserService.create(sysUser);
					
					String realPassWord =sysUserService.encodePassword(sysUser, password);
					sysUser.setPassword(realPassWord);
					sysUserService.update(sysUser);
				}
			}else{//编辑
				SysUser sysUser = sysUserService.get(id);
				sysUser.setUsername(username);
				sysUser.setRealName(realName);
				sysUser.setMobile(mobile);
				SysRole sysRole = null;
				if (sysRoleId != null) {
					sysRole = sysRoleService.get(sysRoleId);
				}
				sysUser.setSysRole(sysRole);
				if(!"".equals(password)){
					String realPassWord =sysUserService.encodePassword(sysUser, password);
					sysUser.setPassword(realPassWord);
				}
				sysUserService.update(sysUser);
			}
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 冻结系统用户
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/freezeSysUser")
	@ResponseBody
	public Result freezeSysUser( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			if(!commonService.validateRes("FreezeSysUser",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = sysUserService.freezeSysUser(ids);
			
			return new Result(true, "成功冻结" + num + "个用户！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
		
	}
	
	/**
	 * 解冻系统用户
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/normalSysUser")
	@ResponseBody
	public Result normalSysUser( @RequestParam(value="ids[]") Integer[] ids,HttpSession session ) {
		
		try {
			if(!commonService.validateRes("NormalSysUser",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = sysUserService.normalSysUser(ids);
			
			return new Result(true, "成功解冻" + num + "个用户！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	/**
	 * 删除系统用户
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delSysUser")
	@ResponseBody
	public Result delSysUser( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			if(!commonService.validateRes("DelSysUser" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = sysUserService.delSysUser(ids);
			
			return new Result(true, "成功删除" + num + "个用户！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	//------------------------------------------------------------------------------
	
	/**
	 * 系统用户列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/sysUserList")
	public String sysUserList(HttpSession session) {
		try {
			if( (!commonService.validateRes("ReadSysUser",session)) 
				&& (!commonService.validateRes("AddSysUser",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			return "/admin/sysUser/sysUserList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载心痛用户列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param username
	 * @param realName
	 * @param mobile
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @return
	 */
	@RequestMapping("/sysUserListData")
	@ResponseBody
	public Object sysUserListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="username", required=false) String username,
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
			param.put("username", username);
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
			List<SysUser> sysUsers = sysUserService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", SysUserVo.transferList(sysUsers));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
