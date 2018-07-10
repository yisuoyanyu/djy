package com.djy.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.djy.admin.vo.CoPartnerTagVo;
import com.djy.co.enumtype.CoPartnerTagType;
import com.djy.co.model.CoPartnerTag;
import com.djy.co.service.CoPartnerService;
import com.djy.co.service.CoPartnerTagService;
import com.djy.sys.service.CommonService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/coPartnerTag/")
public class AdminCoPartnerTagController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoPartnerTagController.class);
	
	@Autowired
	private CoPartnerTagService coPartnerTagService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 调用AdminCoPartnerTagController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartnerTag")
	public CoPartnerTag preloadCoPartnerTag( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coPartnerTagService.get(id);
		}
		return new CoPartnerTag();
	}
	
	/**
	 * 合作商家标签详情
	 * @param id
	 * @param coPartnerId
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerTagRecord")
	public String coPartnerTagRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId,
			Model m ,HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCoPartnerTag" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("coPartnerTag", preloadCoPartnerTag(id));
			m.addAttribute("coPartner", coPartnerService.get(coPartnerId));
			
			m.addAttribute("coPartnerTagType", CoPartnerTagType.values());
			return "/admin/coPartnerTag/coPartnerTagRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交商家标签信息
	 * @param coPartnerTag
	 * @param coPartnerId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoPartnerTag")
	@ResponseBody
	public Result submitCoPartnerTag( 
			@ModelAttribute("preloadCoPartnerTag") CoPartnerTag coPartnerTag,
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId,
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCoPartnerTag",session)) 
					&& (!commonService.validateRes("EditCoPartnerTag",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			coPartnerTag.setCoPartner(coPartnerService.get(coPartnerId));
			coPartnerTagService.saveOrUpdate(coPartnerTag);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 删除商家标签信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCoPartnerTag")
	@ResponseBody
	public Result delCoPartnerTag( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			
			if(!commonService.validateRes("DelCoPartnerTag",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = coPartnerTagService.delCoPartnerTag(ids);
			if(num > 0){
				return new Result(true, "成功删除选中商品标签！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 加载合作商家标签信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param title
	 * @param coPartnerId
	 * @param searchText
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerTagListData")
	@ResponseBody
	public Object coPartnerTagListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			@RequestParam(value="searchText", required=false) String searchText,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("title", title);
			param.put("coPartnerId", coPartnerId);
			param.put("searchText", searchText);
			List<CoPartnerTag> coPartnerTags = coPartnerTagService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerTagVo.transferList(coPartnerTags));
			
			return map;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
