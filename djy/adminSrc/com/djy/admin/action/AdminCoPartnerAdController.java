	package com.djy.admin.action;

import java.util.ArrayList;
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

import com.djy.admin.vo.CoPartnerAdVo;
import com.djy.co.enumtype.CoPartnerAdChoice;
import com.djy.co.enumtype.CoPartnerAdStatus;
import com.djy.co.enumtype.CoPartnerCatgStatus;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.service.CoPartnerAdService;
import com.djy.co.service.CoPartnerService;
import com.djy.sys.service.CommonService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;
import com.frame.upload.Webuploader;

@Controller
@RequestMapping("/admin/coPartnerAd/")
public class AdminCoPartnerAdController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoPartnerAdController.class);
	
	@Autowired
	private CoPartnerAdService coPartnerAdService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 调用AdminCoPartnerAdController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartnerAd")
	public CoPartnerAd preloadCoPartnerAd( @RequestParam(value = "id", required = false) Integer id ) {
		CoPartnerAd coPartnerAd = new CoPartnerAd();
		if ( id != null ) {
			coPartnerAd = coPartnerAdService.get(id);
		}
		return coPartnerAd;
	}
	
	/**
	 * 商家广告信息详情
	 * @param id
	 * @param coPartnerId
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerAdRecord")
	public String coPartnerAdRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId, 
			Model m ,HttpSession session) {
		try {
			
			if(!commonService.validateRes("ReadCoPartnerAd" ,session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";	
			}
			
			CoPartnerAd coPartnerAd = preloadCoPartnerAd(id);
			
			m.addAttribute("coPartnerAd", coPartnerAd);
			m.addAttribute("coPartnerAdStatus", CoPartnerAdStatus.values());
			m.addAttribute("coPartnerAdChoice", CoPartnerAdChoice.values());
			
			m.addAttribute("coPartner", coPartnerService.get(coPartnerId));
			
			return "/admin/coPartnerAd/coPartnerAdRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	/**
	 * 提交商家广告信息
	 * @param coPartnerAd
	 * @param coPartnerId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoPartnerAd")
	@ResponseBody
	public Result submitCoPartnerAd( 
			@ModelAttribute("preloadCoPartnerAd") CoPartnerAd coPartnerAd,
			@RequestParam(value = "coPartnerId", required = false) Integer coPartnerId,
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("AddCoPartnerAd",session)) 
					&& (!commonService.validateRes("EditCoPartnerAd",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			Date now = new Date();
			
			if ( coPartnerAd.getId()== null ) {
				coPartnerAd.setInsertTime(now);
			}
			coPartnerAd.setCoPartner(coPartnerService.get(coPartnerId));
			
			// 处理 上传图片
			if ( Webuploader.existImgUpload() ) {
				
				// 删除不在oldImgs的图片
				if ( ! StringUtil.isEmpty( coPartnerAd.getImgPath() ) ) {
					String[] oldImgs = request.getParameterValues("oldImgs");
					boolean isDel = true;
					if ( oldImgs != null ) {
						for ( int i=0 ; i<oldImgs.length ; i++ ) {
							if ( oldImgs[i].equals( coPartnerAd.getImgPath() ) ) {
								isDel = false;
								break;
							}
						}
					}
					if ( isDel ) {
						String imgPath = coPartnerAd.getImgPath();
						coPartnerAd.setImgPath( null );
						Webuploader.delFile( imgPath );
					}
				}
				
				// 新上传的图片
				List< Map<String, String> > newImgs = new ArrayList<>();
				
				// 拷贝附件到目标路径
				String[] uploadImgs = request.getParameterValues("uploadImgs");
				if (uploadImgs != null) {
					
					// 目标路径
					String imageUploadPath = request.getParameter("imgUploadPath");
					
					for ( int i=0 ; i<uploadImgs.length ; i++ ) {
						String uploadImg = uploadImgs[i];
						int index = uploadImg.indexOf("|");
						String fileName = uploadImg.substring(0, index);
						String tmpPath = uploadImg.substring(index + 1);
						String filePath = Webuploader.moveFileToPath(tmpPath, imageUploadPath);
						
						Map<String, String> map = new HashMap<>();
						map.put("fileName", fileName);
						map.put("filePath", filePath);
						
						newImgs.add( map );
					}
					
				}
				
				// 设置新路径
				if ( newImgs.size() > 0 ) {
					Map<String, String> map = newImgs.get(newImgs.size() - 1);
					coPartnerAd.setImgPath( map.get("filePath") );
				}
				
			}
			
			coPartnerAdService.saveOrUpdate( coPartnerAd );
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
	/**
	 * 删除商家广告信息
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/delCoPartnerAd")
	@ResponseBody
	public Result delCoPartnerAd( @RequestParam(value="ids[]") Integer[] ids ,HttpSession session) {
		try {
			
			if(!commonService.validateRes("DelCoPartnerAd",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			int num = coPartnerAdService.delCoPartnerAd(ids);
			if(num > 0){
				return new Result(true, "成功删除选中商品广告！");
			}else{
				return new Result(false, "删除失败");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 更改商家广告信息状态
	 * @param ids
	 * @param status
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateCoPartnerAd")
	@ResponseBody
	public Result updateCoPartnerAd( @RequestParam(value="ids[]") Integer[] ids ,
			@RequestParam(value="status") Integer status ,HttpSession session) {
		
		try {
			
			if( (!commonService.validateRes("DisableCoPartnerAd",session)) 
					&& (!commonService.validateRes("EnableCoPartnerAd",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			int num = coPartnerAdService.updateCoPartnerAd(ids,status);
			if( num > 0 ){
				if ( status == CoPartnerCatgStatus.normal.getId() ) {
					return new Result(true, "成功启用选中商家广告！");
				} else {
					return new Result(true, "成功禁用选中商家广告！");
				}
				
			}else{
				return new Result(true, "操作失败！");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	//------------------------------------------------------------------------------
	
	/**
	 * 商家广告信息列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerAdList")
	public String coPartnerAdList(HttpSession session) {
		
		if( (!commonService.validateRes("ReadCoPartnerAd",session))){
			logger.error("没有访问权限，请联系系统管理员");
			return "";
		}
		return "/admin/coPartnerAd/coPartnerAdList";
		
	}
	
	/**
	 * 加载商家广告信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param searchText
	 * @param title
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param coPartnerId
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerAdListData")
	@ResponseBody
	public Object coPartnerAdListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			@RequestParam(value="coPartnerId", required=false) Integer coPartnerId,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
		
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("searchText", searchText);
			param.put("title", title);
			
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
			
			List<CoPartnerAd> coPartnerAds = coPartnerAdService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerAdVo.transferList(coPartnerAds));
			
			return map;
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 合作商家广告信息树型加载
	 * @return
	 */
	@RequestMapping("/coPartnerAdTreeData")
	@ResponseBody
	public Result coPartnerAdTreeData(
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			HqlFilter filter = new HqlFilter();
			List<CoPartnerAd> coPartnerAds = new ArrayList<CoPartnerAd>();
			
			filter.addFilter("status", CoPartnerAdStatus.normal.getId());
			coPartnerAds = coPartnerAdService.findByFilter(filter);
			
			map.put("coPartnerAds", CoPartnerAdVo.transferList(coPartnerAds));
			
			return new Result(true, map);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
		}
		
		return new Result(true, map);
		
	}
	
	
}
