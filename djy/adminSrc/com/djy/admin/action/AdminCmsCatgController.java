package com.djy.admin.action;

import java.util.ArrayList;
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

import com.djy.admin.vo.CmsCatgVo;
import com.djy.cms.enumtype.CmsCatgStatus;
import com.djy.cms.model.CmsCatg;
import com.djy.cms.service.CmsCatgService;
import com.djy.sys.service.CommonService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/admin/cmsCatg/")
public class AdminCmsCatgController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCmsCatgController.class);
	
	@Autowired
	private CmsCatgService cmsCatgService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 调用AdminCmsCatgController中的方法都会先调用此方法
	 * @param id
	 * @param parentId
	 * @return
	 */
	@ModelAttribute("preloadCmsCatg")
	public CmsCatg preloadCmsCatg( @RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "parentId", required = false) Integer parentId ) {
		CmsCatg cmsCatg = new CmsCatg();
		if ( id != null ) {
			cmsCatg = cmsCatgService.get(id);
		}else {
			cmsCatg.setParentCmsCatg(cmsCatgService.get(parentId));
		}
		return cmsCatg;
	}
	
	/**
	 * 内容分类详情
	 * @param id
	 * @param parentId
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/cmsCatgRecord")
	public String cmsCatgRecord( 
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "parentId", required = false) Integer parentId, 
			Model m,HttpSession session ) {
		try {
			if ( !commonService.validateRes("ReadCmsCatg" ,session) ){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("cmsCatg", preloadCmsCatg(id,parentId));
			m.addAttribute("cmsCatgStatus", CmsCatgStatus.values());
			if(id != null){
				m.addAttribute("statusName", CmsCatgStatus.fromId(preloadCmsCatg(id,parentId).getStatus()).getValue());
			}
			
			return "/admin/cmsCatg/cmsCatgRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交内容分类信息
	 * @param cmsCatg
	 * @param oldSortNumber
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCmsCatg")
	@ResponseBody
	public Result submitCmsCatg( 
			@ModelAttribute("preloadCmsCatg") CmsCatg cmsCatg,
			@RequestParam(value = "oldSortNumber", required = false) Integer oldSortNumber,
			HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCmsCatg" ,session)) 
					&& (!commonService.validateRes("EditCmsCatg",session)) ){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			boolean isNew = false;
			if ( cmsCatg.getId()== null ) {
				cmsCatg.setInsertTime(new Date());
				isNew = true;
			}
			
			int sortNumber = cmsCatg.getSortNumber();
			
			CmsCatg sortNumCmsCatg = cmsCatgService.getBySortNumberACmsCatg(sortNumber,cmsCatg.getParentCmsCatg());
			
			if ( isNew ) {
				if (sortNumCmsCatg != null) {
					return new Result(false, "已存在该排序号的同级目录，请从新输入排序号！");
				}
				Integer id = (Integer) cmsCatgService.save(cmsCatg);
				
				String idPath = id + "/";
				
				CmsCatg parentCmsCatg = cmsCatg.getParentCmsCatg();
				
				if (parentCmsCatg != null) {
					parentCmsCatg.setIsParent(true);
					cmsCatgService.saveOrUpdate(parentCmsCatg);
					cmsCatg.setParentIdPath(parentCmsCatg.getIdPath());
					idPath = parentCmsCatg.getIdPath() + idPath;
					
					String namePath = "";
					while (parentCmsCatg != null) {
						namePath = parentCmsCatg.getName() + "/" + namePath;
						parentCmsCatg = parentCmsCatg.getParentCmsCatg();
					}
					namePath = namePath + cmsCatg.getName();
					
					cmsCatg.setNamePath(namePath);
					
				} else {
					cmsCatg.setNamePath(cmsCatg.getName());
				}
				cmsCatg.setIdPath(idPath);
				cmsCatgService.saveOrUpdate(cmsCatg);
				
				
			} else {
				//编辑时已存在相同排序号的同级目录，该同级目录与要编辑的当前目录交换排序号
				if (sortNumCmsCatg != null) {
					
					sortNumCmsCatg.setSortNumber(oldSortNumber);
					cmsCatgService.saveOrUpdate(sortNumCmsCatg);
					
				}
				cmsCatgService.saveOrUpdate(cmsCatg);
				
			}
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 删除内容分类信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCmsCatg")
	@ResponseBody
	public Result delCmsCatg( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {

			if(!commonService.validateRes("DelCmsCatg",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsCatgService.delCmsCatg(ids);
			if(num > 0){
				return new Result(true, "成功删除选中内容分类及其子目录！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	/**
	 * 禁用内容分类信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/disableCmsCatg")
	@ResponseBody
	public Result disableCmsCatg( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			
			if(!commonService.validateRes("DisableCmsCatg",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsCatgService.disableCmsCatg(ids);
			if(num > 0){
				return new Result(true, "成功禁用选中内容分类及其子目录！");
			}else{
				return new Result(true, "禁用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	/**
	 * 启用内容分类信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/enableCmsCatg")
	@ResponseBody
	public Result enableCmsCatg( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			
			if(!commonService.validateRes("EnableCmsCatg",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsCatgService.enableCmsCatg(ids);
			if(num > 0){
				return new Result(true, "成功启用选中内容分类及其子目录！");
			}else{
				return new Result(true, "启用失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	// ------------------------------------------------------
	
	/**
	 * 内容分类树型
	 * @param session
	 * @return
	 */
	@RequestMapping("/cmsCatgTree")
	public String cmsCatgTree(HttpSession session) {
		try {
			if( (!commonService.validateRes("ReadCmsCatg",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";	
			}
			return "/admin/cmsCatg/cmsCatgTree";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
	
	}
	
	/**
	 * 内容分类信息加载
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/cmsCatgTreeData")
	@ResponseBody
	public Result cmsCatgTreeData(
			@RequestParam(value="parentId", required=false) Integer parentId
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			HqlFilter filter = new HqlFilter();
			List<CmsCatg> cmsCatgs = new ArrayList<CmsCatg>();
			
			filter.addFilter("parentCmsCatg.id", parentId);
			filter.addOrder("sortNumber", false);
			cmsCatgs = cmsCatgService.findByFilter(filter);
			
			map.put("cmsCatgs", CmsCatgVo.transferList(cmsCatgs));
			
			return new Result(true, map);
			
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
}
