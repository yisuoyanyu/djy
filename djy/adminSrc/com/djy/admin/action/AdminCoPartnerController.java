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

import com.djy.admin.vo.CoPartnerVo;
import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerImg;
import com.djy.co.service.CoPartnerImgService;
import com.djy.co.service.CoPartnerService;
import com.djy.sys.model.SysEmpl;
import com.djy.sys.service.CommonService;
import com.djy.sys.service.SysEmplService;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;
import com.frame.upload.Webuploader;


@Controller
@RequestMapping("/admin/coPartner/")
public class AdminCoPartnerController extends WebController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminCoPartnerController.class);
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private CoPartnerImgService coPartnerImgService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysEmplService sysEmplService;
	
	@Autowired
	private CommonService commonService;
	
	
	
	/**
	 * 调用AdminCoPartnerController中的方法都会先调用此方法
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
			
			if( !commonService.validateRes("ReadCoPartner" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
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
			
			return "/admin/coPartner/coPartnerRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 审核合作商家信息
	 * @param coPartner
	 * @param userId
	 * @param userMobile
	 * @param sysEmplId
	 * @param sysEmplMobile
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
			@RequestParam(value = "userMobile", required = false) String userMobile,
			@RequestParam(value = "sysEmplId", required = false) Integer sysEmplId,
			@RequestParam(value = "sysEmplMobile", required = false) String sysEmplMobile,
			@RequestParam(value="password", required=false) String password, 
			@RequestParam(value="oldPassword", required=false) String oldPassword, 
			HttpServletRequest request,HttpSession session) {
		try {
			
			if( (!commonService.validateRes("ReadCoPartner",session)) 
					&& (!commonService.validateRes("EditCoPartner",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			if (coPartner.getUser() == null) {
				User user = userService.getUserByMobile(userMobile);
				if (user != null) {
					//判断通过该手机号查询到的会员是否已匹配过其它合作商家
					if (user.getCoPartner() != null && user.getCoPartner().getId() != coPartner.getId()) {
						return new Result(false, "关联会员手机对应会员已存在相匹配的合作商家，请重新输入关联会员手机！");
					}
					//判断通过该手机号查询到的会员是否已匹配过其他职员
					if (user.getSysEmpl() != null && user.getSysEmpl().getId() != sysEmplId) {
						return new Result(false, "关联会员手机对应会员已存在相匹配的职员，请重新输入关联会员手机！");
					}
				} else {
					return new Result(false, "不存在该手机号对应会员，请重新输入关联会员手机！");
				}
			}
			
			if (!StringUtil.isBlank(sysEmplMobile)) {
				SysEmpl sysEmpl = sysEmplService.getSysEmplByMobile(sysEmplMobile);
				if (sysEmpl != null) {
					coPartner.setSysEmpl(sysEmpl);
				} else {
					return new Result(false, "不存在该手机号对应职员，请重新输入所属职员手机！");
				}
			}
			
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
			
			
			return new Result(true, "审核成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
	}
	
	
	/**
	 * 更改合作商家状态
	 * @param ids
	 * @param status
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateCoPartner")
	@ResponseBody
	public Result updateCoPartner( @RequestParam(value="ids[]") Integer[] ids ,
			@RequestParam(value="status") Integer status ,HttpSession session) {
		
		try {
			
			if( (!commonService.validateRes("EditCoPartner",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			int num = coPartnerService.updateCoPartner(ids,status);
			if( num > 0 ){
				if ( status == CoPartnerStatus.normal.getId() ) {
					return new Result(true, "成功启用选中合作商家！");
				} else {
					return new Result(true, "成功禁用选中合作商家！");
				}
				
			}else{
				return new Result(true, "操作失败！");
			}
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	/**
	 * 合作商家列表
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerList")
	public String coPartnerList(HttpSession session) {
		try {
			
			if( !commonService.validateRes("ReadCoPartner" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			return "/admin/coPartner/coPartnerList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	/**
	 * 加载合作商家信息
	 * @param pbPageSize
	 * @param pbStart
	 * @param sortName
	 * @param sortOrder
	 * @param name
	 * @param mobile
	 * @param orgOrStoreId
	 * @param searchText
	 * @param insertTimeStart
	 * @param insertTimeEnd
	 * @param session
	 * @return
	 */
	@RequestMapping("/coPartnerListData")
	@ResponseBody
	public Object coPartnerListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder,
			@RequestParam(value="status", required=false) Integer status,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="mobile", required=false) String mobile,
			@RequestParam(value="searchText", required=false) String searchText,
			@RequestParam(value="insertTimeStart", required=false) String insertTimeStart,
			@RequestParam(value="insertTimeEnd", required=false) String insertTimeEnd,
			HttpSession session
		) {
		try {
			PagingBean pb = new PagingBean(pbStart, pbPageSize);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sortName", sortName);
			param.put("sortOrder", sortOrder);
			param.put("status", status);
			param.put("name", name);
			param.put("mobile", mobile);
			param.put("searchText", searchText);
			
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
			
			List<CoPartner> coPartners = coPartnerService.search(pb, param);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", pb.getTotalItems());
			map.put("rows", CoPartnerVo.transferList(coPartners));
			
			return map;
			
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}
	
	/**
	 * 合作商家信息树型加载
	 * @return
	 */
	@RequestMapping("/coPartnerTreeData")
	@ResponseBody
	public Result coPartnerTreeData(
			@RequestParam(value="coMode", required=false) Integer coMode
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			HqlFilter filter = new HqlFilter();
			List<CoPartner> coPartners = new ArrayList<CoPartner>();
			
			filter.addFilter("status", CoPartnerStatus.normal.getId());
			if (coMode != null) {
				filter.addFilter("coMode", coMode);
			}
			coPartners = coPartnerService.findByFilter(filter);
			
			map.put("coPartners", CoPartnerVo.transferList(coPartners));
			
			return new Result(true, map);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("",e);
		}
		
		return new Result(true, map);
		
	}
	
}
