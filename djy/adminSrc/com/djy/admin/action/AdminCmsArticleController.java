package com.djy.admin.action;

import java.util.Date;
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

import com.djy.admin.vo.CmsArticleVo;
import com.djy.cms.enumtype.CmsArticleStatus;
import com.djy.cms.model.CmsArticle;
import com.djy.cms.model.CmsCatg;
import com.djy.cms.service.CmsArticleService;
import com.djy.cms.service.CmsCatgService;
import com.djy.sys.service.CommonService;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/admin/cmsArticle/")
public class AdminCmsArticleController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCmsArticleController.class);
	
	@Autowired
	private CmsArticleService cmsArticleService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CmsCatgService cmsCatgService;
	
	/**
	 * 调用AdminCmsArticleController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCmsArticle")
	public CmsArticle preloadCmsArticle( @RequestParam(value = "id", required = false) Integer id ) {
		CmsArticle cmsArticle = new CmsArticle();
		if ( id != null ) {
			cmsArticle = cmsArticleService.get(id);
		}
		return cmsArticle;
	}
	
	/**
	 * 文章信息详情
	 * @param id
	 * @param request
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/cmsArticleRecord")
	public String cmsArticleRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			HttpServletRequest request,Model m ,HttpSession session) {
		try {
			if(!commonService.validateRes("ReadCmsArticle" ,session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";	
			}
			
			String articleUrl = request.getServletPath() //请求的相对url 
				    + "?" + request.getQueryString(); //请求参数
			
			String [] arrArticleUrl = articleUrl.split("/admin/");
			
			m.addAttribute("articleUrl", arrArticleUrl[1].toString());
			
			CmsArticle cmsArticle = preloadCmsArticle(id);
			
			if (cmsArticle.getId() != null) {
				CmsCatg cmsCatg = cmsArticle.getCmsCatg();
				m.addAttribute("cmsCatg", cmsCatg);
			}
			
			m.addAttribute("cmsArticle", cmsArticle);
			m.addAttribute("cmsArticleStatus", CmsArticleStatus.values());
			return "/admin/cmsArticle/cmsArticleRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 提交文章信息
	 * @param cmsArticle
	 * @param content
	 * @param cmsCatgId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCmsArticle")
	@ResponseBody
	public Result submitCmsArticle( 
			@ModelAttribute("preloadCmsArticle") CmsArticle cmsArticle,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "cmsCatgId", required = false) Integer cmsCatgId,
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCmsArticle",session)) 
				&& (!commonService.validateRes("EditCmsArticle",session)) ){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			if ( cmsArticle.getId()== null ) {
				cmsArticle.setInsertTime(new Date());
			}
			
			cmsArticle.setCmsCatg(cmsCatgService.get(cmsCatgId));
			cmsArticle.setContent(content);
			
			cmsArticleService.saveOrUpdate(cmsArticle);
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	/**
	 * 禁用文章信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/disableCmsArticle")
	@ResponseBody
	public Result disableCmsArticle( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			if(!commonService.validateRes("DisableCmsArticle",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsArticleService.disableCmsArticle(ids);
			
			return new Result(true, "成功禁用" + num + "篇文章！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
		
	}
	
	/**
	 * 启用文章信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/enableCmsArticle")
	@ResponseBody
	public Result enableCmsArticle( @RequestParam(value="ids[]") Integer[] ids,HttpSession session ) {
		
		try {
			if(!commonService.validateRes("EnableCmsArticle",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsArticleService.enableCmsArticle(ids);
			
			return new Result(true, "成功启用" + num + "篇文章！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 删除文章
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCmsArticle")
	@ResponseBody
	public Result delCmsArticle( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		
		try {
			if(!commonService.validateRes("DelCmsArticle" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = cmsArticleService.delCmsArticle(ids);
			
			return new Result(true, "成功删除" + num + "篇文章！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false,"网络异常,请联系系统管理员！");
		}
		
	}
	
	
	//------------------------------------------------------------------------------
	
	/**
	 * 文章信息列表
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/cmsArticleList")
	public String cmsArticleList(Model m ,HttpSession session) {
		try {
			if( (!commonService.validateRes("ReadCmsArticle",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/cmsArticle/cmsArticleList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载文章列表信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param code
	 * @param title
	 * @param cmsCatgId
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @return
	 */
	@RequestMapping("/cmsArticleListData")
	@ResponseBody
	public Object cmsArticleListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="code", required=false) String code,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="cmsCatgId", required=false) String cmsCatgId,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("code", code);
			param.put("title", title);
			param.put("cmsCatgId", cmsCatgId);
			
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
			
			List<CmsArticle> cmsArticles = cmsArticleService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CmsArticleVo.transferList(cmsArticles));
			
			return map;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	
}
