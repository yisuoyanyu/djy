package com.djy.partner.action;

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

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerImg;
import com.djy.co.service.CoPartnerImgService;
import com.djy.co.service.CoPartnerService;
import com.djy.user.service.UserService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.upload.Webuploader;


@Controller
@RequestMapping("/partner/coPartner/")
public class PartnerCoPartnerController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCoPartnerController.class);
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CoPartnerImgService coPartnerImgService;
	
	@Autowired
	private UserService userService;
	
	
	
	/**
	 * 调用PartnerCoPartnerController中的方法都会先调用此方法
	 * @param id
	 * @return
	 */
	@ModelAttribute("preloadCoPartner")
	public CoPartner preloadCoPartner( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coPartnerService.get(id);
		}
		return new CoPartner();
	}
	
	/**
	 * 合作商家信息详情
	 * @param id
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerRecord")
	public String coPartnerRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m ,HttpSession session) {
		try {
			
			m.addAttribute("coPartner", preloadCoPartner(id));
			
			m.addAttribute("coPartnerStatus", CoPartnerStatus.values());
			
			if (id != null) {
				m.addAttribute("statusName", CoPartnerStatus.fromId(preloadCoPartner(id).getStatus()).getValue());
				if (preloadCoPartner(id).getCoMode() != null) {
					m.addAttribute("coModeName", CoPartnerMode.fromId(preloadCoPartner(id).getCoMode()).getValue());
				}
			}
			
			m.addAttribute("coPartnerModes", CoPartnerMode.values());
			
			m.addAttribute("normalStatus", CoPartnerStatus.normal.getId());
			
			m.addAttribute("sysDepositMode", CoPartnerMode.sysDeposit.getId());
			
			return "/partner/coPartner/coPartnerRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 修改密码
	 * @param coPartner
	 * @param userId
	 * @param password
	 * @param oldPassword
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitCoPartner")
	@ResponseBody
	public Result submitCoPartner( 
			@ModelAttribute("preloadCoPartner") CoPartner coPartner, 
			@RequestParam(value = "userId", required = false) Integer userId,
			@RequestParam(value="password", required=false) String password, 
			@RequestParam(value="oldPassword", required=false) String oldPassword, 
			HttpServletRequest request,HttpSession session) {
		try {
			
			Date now = new Date();
			
			if ( coPartner.getId()== null ) { //新增
				coPartner.setInsertTime(now);
				//后台新增合作商家默认为正常
				coPartner.setStatus(CoPartnerStatus.normal.getId());
				
				coPartnerService.create(coPartner);
				String realPassword =coPartnerService.encodePassword(coPartner, password);
				coPartner.setPassword(realPassword);
			} else { //编辑
				if(!"".equals(password)){
					String realPassword =coPartnerService.encodePassword(coPartner, password);
					coPartner.setPassword(realPassword);
				} else {
					coPartner.setPassword(oldPassword);
				}
			}
			coPartner.setUser(userService.get(userId));
			
			if (coPartner.getCoMode() == CoPartnerMode.perOrder.getId()) {
				coPartner.setMinSysDeposit(null);
				coPartner.setMaxSysDeposit(null);
			}
			
			//------------------------------LOGO图片----------------------------------------
			if ( Webuploader.existImgUpload("logoUpload") ) {
				// 删除不在oldImgs的图片
				if ( ! StringUtil.isEmpty( coPartner.getLogoPath() ) ) {
					String[] oldImgs = request.getParameterValues("oldLogos");
					boolean isDel = true;
					if ( oldImgs != null ) {
						for ( int i=0 ; i<oldImgs.length ; i++ ) {
							if ( oldImgs[i].equals( coPartner.getLogoPath() ) ) {
								isDel = false;
								break;
							}
						}
					}
					if ( isDel ) {
						String imgPath = coPartner.getLogoPath();
						coPartner.setLogoPath( null );
						Webuploader.delFile( imgPath );
					}
				}
				
				// 新上传的图片
				List< Map<String, String> > newImgs = new ArrayList<>();
				
				// 拷贝附件到目标路径
				String[] uploadImgs = request.getParameterValues("uploadLogos");
				if (uploadImgs != null) {
					
					// 目标路径
					String imageUploadPath = request.getParameter("logoUploadPath");
					
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
					coPartner.setLogoPath( map.get("filePath") );
				}
				
			}
			
			coPartnerService.saveOrUpdate(coPartner);
			
			//--------------------------合作商家图片------------------------------------------
			if ( Webuploader.existImgUpload("imageUpload") ) {
				// 删除 原有图片中 已被删除的图片
				List<CoPartnerImg> coPartnerImgs = coPartner.getCoPartnerImgs();
				if ( coPartnerImgs != null ) {
					// oldImgs 为 原有图片中 保留下来的图片
					String[] oldImgs = request.getParameterValues("oldImgs");
					
					for ( CoPartnerImg coPartnerImg : coPartnerImgs ) {
						boolean isDel = true;
						if ( oldImgs != null ) {
							for ( int i=0 ; i<oldImgs.length ; i++ ) {
								if ( oldImgs[i].equals( coPartnerImg.getImgPath() ) ) {
									isDel = false;
									break;
								}
							}
						}
						if ( isDel ){
							coPartnerImgService.delCoPartnerImg(coPartnerImg);
						}
					}
				}
				
				// 新上传的图片
				List< Map<String, String> > newImgs = new ArrayList<>();
				
				// 拷贝附件到目标路径
				String[] uploadImgs = request.getParameterValues("uploadImgs");
				if (uploadImgs != null) {
					
					// 目标路径
					String imageUploadPath = request.getParameter("imgUploadPath");
					
					for ( int i=0 ; i < uploadImgs.length ; i++ ) {
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
				
				// 新建 新上传的图片
				for (int i=0; i<newImgs.size(); i++) {
					Map<String, String> map = newImgs.get(i);
					CoPartnerImg coPartnerImg = new CoPartnerImg();
					coPartnerImg.setCoPartner(coPartner);
					coPartnerImg.setTitle( map.get("fileName") );
					coPartnerImg.setImgPath( map.get("filePath") );
					coPartnerImgService.saveOrUpdate( coPartnerImg );
				}
			}
			
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
}
