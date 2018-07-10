package com.djy.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frame.base.web.controller.WebController;
import com.frame.upload.Webuploader;

@Controller
@RequestMapping("/admin/fileUpload/")
public class AdminFileUploadController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminFileUploadController.class);
	
	/**
	 * 文件上传
	 * @param file
	 * @param path
	 * @param namePrefix
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/webuploader.json")
	@ResponseBody
	public void webuploader(
			@RequestParam("file") MultipartFile file,
			@RequestParam("path") String path,
			@RequestParam(value = "namePrefix", required = false, defaultValue = "") String namePrefix,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String result = Webuploader.upload(file, path, namePrefix, false);
		
		response.setContentType("text/html;charset=utf-8");
		
		try {
			response.getWriter().print(result);
		} catch(Exception e) {
			//e.printStackTrace();
			logger.error("",e);
		}
		
	}
	
}
