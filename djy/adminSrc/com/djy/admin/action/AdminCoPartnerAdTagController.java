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

import com.djy.admin.vo.CoPartnerAdTagVo;
import com.djy.co.enumtype.CoPartnerAdTagType;
import com.djy.co.model.CoPartnerAdTag;
import com.djy.co.service.CoPartnerAdService;
import com.djy.co.service.CoPartnerAdTagService;
import com.djy.sys.service.CommonService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;


@Controller
@RequestMapping("/admin/coPartnerAdTag/")
public class AdminCoPartnerAdTagController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoPartnerAdTagController.class);
	
	@Autowired
	private CoPartnerAdTagService coPartnerAdTagService;
	
	@Autowired
	private CoPartnerAdService coPartnerAdService;
	
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 调用AdminCoPartnerAdTagController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartnerAdTag")
	public CoPartnerAdTag preloadCoPartnerAdTag( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coPartnerAdTagService.get(id);
		}
		return new CoPartnerAdTag();
	}
	
	/**
	 * 商家广告活动标签详情
	 * @param id
	 * @param coPartnerAdId
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerAdTagRecord")
	public String coPartnerAdTagRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerAdId", required = false) Integer coPartnerAdId,
			Model m ,HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCoPartnerAdTag" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("coPartnerAdTag", preloadCoPartnerAdTag(id));
			m.addAttribute("coPartnerAd", coPartnerAdService.get(coPartnerAdId));
			
			m.addAttribute("coPartnerAdTagType", CoPartnerAdTagType.values());
			return "/admin/coPartnerAdTag/coPartnerAdTagRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交商家广告活动标签
	 * @param coPartnerAdTag
	 * @param coPartnerAdId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoPartnerAdTag")
	@ResponseBody
	public Result submitCoPartnerAdTag( 
			@ModelAttribute("preloadCoPartnerAdTag") CoPartnerAdTag coPartnerAdTag,
			@RequestParam(value = "coPartnerAdId", required = false) Integer coPartnerAdId,
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCoPartnerAdTag",session)) 
					&& (!commonService.validateRes("EditCoPartnerAdTag",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			coPartnerAdTag.setCoPartnerAd(coPartnerAdService.get(coPartnerAdId));
			coPartnerAdTagService.saveOrUpdate(coPartnerAdTag);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 删除商家广告活动标签
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCoPartnerAdTag")
	@ResponseBody
	public Result delCoPartnerAdTag( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			
			if(!commonService.validateRes("DelCoPartnerAdTag",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = coPartnerAdTagService.delCoPartnerAdTag(ids);
			if(num > 0){
				return new Result(true, "成功删除选中商家广告活动标签！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 加载商家广告活动标签
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param title
	 * @param coPartnerAdId
	 * @param searchText
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerAdTagListData")
	@ResponseBody
	public Object coPartnerAdTagListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="coPartnerAdId", required=false) Integer coPartnerAdId,
			@RequestParam(value="searchText", required=false) String searchText,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("title", title);
			param.put("coPartnerAdId", coPartnerAdId);
			param.put("searchText", searchText);
			List<CoPartnerAdTag> coPartnerAdTags = coPartnerAdTagService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerAdTagVo.transferList(coPartnerAdTags));
			
			return map;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
