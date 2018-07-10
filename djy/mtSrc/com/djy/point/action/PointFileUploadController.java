package com.djy.point.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frame.base.web.controller.WebController;
import com.frame.upload.Webuploader;

@Controller
@RequestMapping("/point/fileUpload/")
public class PointFileUploadController extends WebController {

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
			e.printStackTrace();
		}
		
	}
	
}
