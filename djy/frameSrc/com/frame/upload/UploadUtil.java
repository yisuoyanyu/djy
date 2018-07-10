package com.frame.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UploadUtil {
	
	
	/**
	 * 计算随机文件名
	 * @return
	 */
	public static String getRandomName(String fileName) {
		Random random = new Random();
		return fileName = "" + random.nextInt(10000)
				+ System.currentTimeMillis() 
				+ fileName.substring(fileName.lastIndexOf("."));
	}
	
	
	/**
	 * 计算相对路径对应的物理路径
	 * @param path
	 * @return
	 */
	public static String getPhysicalPath(String path) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		String realPath = request.getSession().getServletContext().getRealPath(path);
		int tmp = realPath.indexOf("webapps");
		String split = realPath.substring(tmp+7, tmp+8);
		String webapps = realPath.substring(0, tmp + 8);
		String tmpStr = realPath.substring(tmp + 8);
		String ret = webapps + tmpStr.substring(tmpStr.indexOf(split) + 1);
		return ret;
		
	}
	
	
	/**
	 * 获取path下 今天 对应的文件夹，如果不存在，则马上新建
	 * @param path 
	 * @return 
	 */
	public static String getTodayPath(String path) {
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		path += "/" + formater.format(new Date());
		
		File dir = new File(getPhysicalPath(path));
		
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				return "";
			}
		}
		return path;
		
	}
	
	
	/**
	 * 获取带域名的服务器路径
	 * @return 如 http://127.0.0.1:8080
	 */
	public static String getBasePath() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		String basePath = "";
		if (request==null)
			return basePath;
			
		String scheme = request.getScheme();			// 协议，http还是https
		String serverName = request.getServerName();	// 服务器名
		int port = request.getServerPort();				// 端口号
		
		if (port==80) {
			basePath = scheme + "://" + serverName;
		} else {
			basePath = scheme + "://" + serverName + ":" + port;
		}
		
		return basePath;
		
	}
	
	
}
