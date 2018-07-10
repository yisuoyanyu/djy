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

import com.djy.admin.vo.SysPrtTmplVo;
import com.djy.sys.model.SysPrtTmpl;
import com.djy.sys.service.CommonService;
import com.djy.sys.service.SysPrtTmplService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.PagingBean;
import com.frame.base.web.vo.Result;
import com.frame.upload.Webuploader;

@Controller
@RequestMapping("/admin/sysPrtTmpl/")
public class AdminSysPrtTmplController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger( AdminSysPrtTmplController.class );
	
	
	@Autowired
	private SysPrtTmplService sysPrtTmplService;
	
	@Autowired
	private CommonService commonService;
	
	
	
	@ModelAttribute("preloadSysPrtTmpl")
	public SysPrtTmpl preloadSysPrtTmpl( @RequestParam(value = "id", required = false) Integer id ) {
		if ( id != null ) {
			return sysPrtTmplService.get( id );
		}
		return new SysPrtTmpl();
	}
	
	
	@RequestMapping("/sysPrtTmplRecord")
	public String sysPrtTmplRecord( 
			@RequestParam(value = "id", required = false) Integer id, 
			Model m ,HttpSession session) {
		try {
			if( !commonService.validateRes("ReadSysPrtTmpl" , session)){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			
			m.addAttribute("sysPrtTmpl", preloadSysPrtTmpl(id));
			
			return "/admin/sysPrtTmpl/sysPrtTmplRecord";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	
	@RequestMapping("/submitSysPrtTmpl")
	@ResponseBody
	public Result submitSysPrtTmpl(
			@ModelAttribute("preloadSysPrtTmpl") SysPrtTmpl sysPrtTmpl, 
			HttpServletRequest request ,HttpSession session
	) {
		
		try {
			
			if( (!commonService.validateRes("AddSysPrtTmpl",session)) 
					&& (!commonService.validateRes("EditSysPrtTmpl",session)) ){
					logger.error("没有访问权限，请联系系统管理员");
					return new Result(false,"no access");	
			}
			
			boolean isNew = false;
			if ( sysPrtTmpl.getId() == null ) isNew = true;
			
			if ( isNew ) {
				sysPrtTmpl.setInsertTime( new Date() );
			}
			
			sysPrtTmplService.saveOrUpdate( sysPrtTmpl );
			
			if ( Webuploader.existFileUpload() ) {
				// 删除不在oldFiles的文件
				if ( ! StringUtil.isEmpty( sysPrtTmpl.getFilePath() ) ) {
					String[] oldFiles = request.getParameterValues("oldFiles");
					boolean isDel = true;
					if ( oldFiles != null ) {
						for ( int i=0 ; i<oldFiles.length ; i++ ) {
							if ( oldFiles[i].equals( sysPrtTmpl.getFilePath() ) ) {
								isDel = false;
								break;
							}
						}
					}
					if ( isDel ) {
						String filePath = sysPrtTmpl.getFilePath();
						sysPrtTmpl.setFileTitle( null );
						sysPrtTmpl.setFilePath( null );
						Webuploader.delFile( filePath );
					}
				}
				
				// 新上传的文件
				List< Map<String, String> > newFiles = new ArrayList<>();
				
				// 拷贝附件到目标文件
				String[] uploadFiles = request.getParameterValues("uploadFiles");
				if ( uploadFiles != null ) {
					String fileUploadPath = request.getParameter("fileUploadPath");
					for ( int i=0 ; i < uploadFiles.length ; i++ ) {
						String uploadFile = uploadFiles[i];
						int index = uploadFile.indexOf("|");
						String fileName = uploadFile.substring(0, index);
						String tmpPath = uploadFile.substring(index + 1);
						String filePath = Webuploader.moveFileToPath(tmpPath, fileUploadPath);
						
						Map<String, String> map = new HashMap<>();
						map.put("fileName", fileName);
						map.put("filePath", filePath);
						
						newFiles.add( map );
					}
				}
				
				// 设置新路径
				if ( newFiles.size() > 0 ) {
					Map<String, String> map = newFiles.get(newFiles.size() - 1);
					sysPrtTmpl.setFileTitle( map.get("fileName") );
					sysPrtTmpl.setFilePath( map.get("filePath") );
				}
				
				sysPrtTmplService.saveOrUpdate( sysPrtTmpl );
			}
			
			return new Result(true, "提交成功");
		} catch(Exception e) {
			logger.error("", e);
			return new Result(false, "程序错误，请联系管理员！");
		}
		
	}
	
	
	@RequestMapping("/delSysPrtTmpl")
	@ResponseBody
	public Result delSysPrtTmpl( @RequestParam(value="ids[]") Integer[] ids,HttpSession session ) {
		
		try {
			if(!commonService.validateRes("DelSysPrtTmpl",session)){
				logger.error("没有访问权限，请联系系统管理员");
				return new Result(false,"no access");	
			}
			
			if (ids.length == 0) {
				return new Result(false, "没选中用户！");
			}
			
			int num = sysPrtTmplService.delSysPrtTmpl(ids);
			
			return new Result(true, "成功删除" + num + "个套打模板！");
		} catch (Exception e) {
			logger.error("",e);
			return new Result(false, "网络异常,请联系系统管理员！");
		}
		
	}
	
	
	// ------------------------------------------------------
	
	@RequestMapping("/sysPrtTmplList")
	public String sysPrtTmplList(HttpSession session) {
		
		try {
			if( (!commonService.validateRes("ReadSysPrtTmpl",session))){
				logger.error("没有访问权限，请联系系统管理员");
				return "";
			}
			return "/admin/sysPrtTmpl/sysPrtTmplList";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
		
	}
	
	@RequestMapping("/sysPrtTmplListData")
	@ResponseBody
	public Object sysPrtTmplListData(
			@RequestParam(value="pbPageSize", required=false) Integer pbPageSize,
			@RequestParam(value="pbStart", required=false) Integer pbStart,
			@RequestParam(value="sortName", required=false) String sortName,
			@RequestParam(value="sortOrder", required=false) String sortOrder
		) {
		
		PagingBean pb = new PagingBean(pbStart, pbPageSize);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sortName", sortName);
		param.put("sortOrder", sortOrder);
		
		List<SysPrtTmpl> sysPrtTmpls = sysPrtTmplService.search(pb, param);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pb.getTotalItems());
		map.put("rows", SysPrtTmplVo.transferList(sysPrtTmpls));
		
		return map;
		
	}
	
	
}
