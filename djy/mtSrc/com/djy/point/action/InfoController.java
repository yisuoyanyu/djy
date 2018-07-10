package com.djy.point.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerImg;
import com.djy.co.service.CoPartnerImgService;
import com.djy.co.service.CoPartnerService;
import com.djy.point.vo.WxConfigVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.djy.utils.BeanUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.upload.Webuploader;
import com.frame.wechat.api.utils.SignUtil;

@Controller
@RequestMapping("/point/info")
public class InfoController extends WebController {
	private static Logger logger = LoggerFactory.getLogger( InfoController.class );

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CoPartnerImgService coPartnerImgService;
	
	
	@ModelAttribute("preloadCoPartner")
	public CoPartner preloadCoPartner( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return coPartnerService.get( id );
		}
		return new CoPartner();
	}
	
	
	@ModelAttribute("/coPartner")
	public CoPartner getCoPartner(
		@RequestParam(value = "id", required = false) Integer id
	) {
		if (id != null) {
			return this.coPartnerService.get(id);
		}
		return new CoPartner();
	}
	
	
	@RequestMapping(value = "/submitAuthor.do")//在关注以后直接就创建copartner
	@ResponseBody
	public Result submitAuthor(
			@RequestParam(value="userId") Integer userId,
			@ModelAttribute("preloadCoPartner") CoPartner coPartner,
			HttpSession session, HttpServletRequest request
	) {
		
		try {
			
			if ( ! userId.equals( (Integer) session.getAttribute("userId") ) ) {
				return new Result(false, "用户身份有误，请联系系统管理员！");
			}
			
			User user = userService.getById( userId );
			coPartner.setUser( user );
			if (null == coPartner.getStatus() || coPartner.getStatus() != CoPartnerStatus.normal.getId()) {
				coPartner.setStatus(CoPartnerStatus.supplement.getId());//设置为“补充完信息”状态
			}
			
			coPartnerService.saveOrUpdate( coPartner );
			
			// 处理 上传图片
			if ( Webuploader.existImgUpload() ) {
				
				// 删除 原有图片中 已被删除的图片
				List<CoPartnerImg> coPartnerImgs = coPartner.getCoPartnerImgs();
				if ( coPartnerImgs != null ) {
					// oldImgs 为 原有图片中 保留下来的图片
					String[] oldImgs = request.getParameterValues("oldImgs");
					for ( CoPartnerImg coPartnerImg : coPartnerImgs ) {
						boolean isDel = true;
						if ( oldImgs != null ) {
							for ( int i=0 ; i < oldImgs.length ; i++ ) {
								if ( oldImgs[i].equals( coPartnerImg.getImgPath() ) ) {
									isDel = false;
									break;
								}
							}
						}
						if ( isDel )
							coPartnerImgService.delCoPartnerImg( coPartnerImg );
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
				for ( int i=0 ; i<newImgs.size() ; i++ ) {
					Map<String, String> map = newImgs.get(i);
					CoPartnerImg coPartnerImg = new CoPartnerImg();
					coPartnerImg.setCoPartner( coPartner );
					coPartnerImg.setTitle( map.get("fileName") );
					coPartnerImg.setImgPath( map.get("filePath") );
					coPartnerImgService.saveOrUpdate( coPartnerImg );
				}
				
			}
			
			return new Result(true, "提交成功");
		} catch (Exception e) {
			logger.error("", e);
			return new Result(false, e.getMessage());
		}
		
	}
	
	@RequestMapping("/myInfo.do")
	public String myInfo(HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		if (!StringUtils.isBlank(user.getCoPartner().getName())) {//表示已经提交过信息
			CoPartner coPartner = user.getCoPartner();
			m.addAttribute("coPartner", coPartner);
			return "/point/info/myInfo";
		}else {
			WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
			m.addAttribute("wxConfig", wxConfig);
			
			m.addAttribute("user", user);
			return "/point/info/author";
		}
		
	}
	
	@RequestMapping("/myInfoEdit.do")
	public String myInfoEdit(HttpSession session, Model m) {
		Integer userId = (Integer) session.getAttribute("userId");
		User user = userService.getById(userId);
		CoPartner coPartner = user.getCoPartner();
		m.addAttribute("coPartner", coPartner);
		return "/point/info/myInfoEdit";
	}
}
