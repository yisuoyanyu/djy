package com.frame.upload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;
import com.frame.fssclient.FssClientUtil;


public class Webuploader {
	
	// 图片服务器路径
	private static final String appFilePre = "/attachment/";
	
	// 当前没有配置文件服务器时， 图片删除后存储路径
	private static final String delFilePre = "/attachment_del/";
	
	
	/**
	 * 上传文件
	 * @param file 文件流
	 * @param path 上传的路径
	 * @param namePrefix 上传文件保存的文件名前缀
	 * @param isNeedDomain 返回路径是否需要带域名
	 * @return json格式的字符串
	 */
	public static String upload(MultipartFile file, String path, String namePrefix, Boolean isNeedDomain) {
		
		String fileName = UploadUtil.getRandomName(file.getOriginalFilename());
		
		if ( !StringUtils.isEmpty(namePrefix) )
			fileName = namePrefix + "_" + fileName;
		
		String savePath = UploadUtil.getTodayPath(appFilePre + path);
		String filePath = savePath + "/" + fileName;
		
		String physical = UploadUtil.getPhysicalPath(filePath);
		
		try {
			File destFile = new File(physical);
			FileUtils.writeByteArrayToFile(destFile, file.getBytes());
		} catch(IOException e) {
			return "{" + "\"state\": \"FALSE\", " + "}";
		}
		
		String url = filePath;
		if (isNeedDomain != null && isNeedDomain) {
			url = UploadUtil.getBasePath() + url;
		}
		
		String result = "{" 
				+ "\"state\": \"SUCCESS\", " 
				+ "\"originalName\": \"" + file.getOriginalFilename() + "\", " 
				+ "\"type\": \"" + fileName.substring(fileName.lastIndexOf(".")) + "\", " 
				+ "\"size\": " + file.getSize() + ", " 
				+ "\"name\":\"" + fileName + "\", " 
				+ "\"filePath\":\"" + filePath + "\", "
				+ "\"url\": \"" + url + "\"" 
				+ "}";
		
		result = result.replace("\\", "\\\\");		// 将“\”替换成“\\”
		
		return result;
	}
	
	
	public static String moveFileToPath(String filePathSrc, String destPath) {
		
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		
		if ( StringUtil.isEmpty( fssApiSer ) ) {	// 没有配置文件服务器
			
			String srcPhysical = UploadUtil.getPhysicalPath(filePathSrc);
			File srcFile = new File( srcPhysical );
			
			if ( !srcFile.exists() )
				return null;
			
			String todayPath = UploadUtil.getTodayPath(appFilePre + destPath);
			String fileName = filePathSrc.substring(filePathSrc.lastIndexOf("/") + 1);
			String destFilePath = todayPath + "/" + fileName;
			
			String destPhysical = UploadUtil.getPhysicalPath( destFilePath );
			File destFile = new File(destPhysical);
			
			try {
				FileUtils.moveFile(srcFile, destFile);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return destFilePath;
			
		} else {								// 已配置文件服务器
			
			String srcPhysical = UploadUtil.getPhysicalPath(filePathSrc);
			File file = new File(srcPhysical);
			
			String filePath = FssClientUtil.putFile(file, appFilePre + destPath + "/{yyyy}{mm}{dd}/{filename}").getFilePath();
			
			file.delete();
			
			return filePath;
			
		}
	}
	
	
	/**
	 * 删除文件
	 * @param filePath 文件的相对路径
	 */
	public static void delFile(String filePath) {
		
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		
		if ( StringUtil.isEmpty( fssApiSer ) ) {	// 没有配置文件服务器
			
			String srcPhysical = UploadUtil.getPhysicalPath(filePath);
			File srcFile = new File( srcPhysical );
			
			if ( !srcFile.exists() )
				return;
			
			String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
			String destFolderPath = folderPath.replaceFirst(appFilePre,  delFilePre);
			File dir = new File( UploadUtil.getPhysicalPath(destFolderPath) );
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			
			String destFilePath = destFolderPath + "/" + fileName;
			String destPhysical = UploadUtil.getPhysicalPath(destFilePath);
			File destFile = new File(destPhysical);
			
			try {
				FileUtils.moveFile(srcFile, destFile);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} else {								// 已配置文件服务器
			
			FssClientUtil.delFile(filePath);
			
		}
		
	}
	
	
	public static InputStream getFileStream(String filePath) {
		
		InputStream result = null;
		
		String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
		
		if ( StringUtil.isEmpty( fssApiSer ) ) {	// 没有配置文件服务器
			
			InputStream in = null;
			
			try {
				
				String physical = UploadUtil.getPhysicalPath(filePath);
				File file = new File( physical );
				
				in =  new FileInputStream( file );
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				byte[] buffer = new byte[1024];
				int len = -1;
				while ( ( len = in.read( buffer ) ) != -1 ) {
					out.write(buffer, 0, len);
				}
				
				byte[] bytes = out.toByteArray();
				
				result = new ByteArrayInputStream( bytes );
				
			}  catch(IOException e) {
				
				e.printStackTrace();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			} finally {
				
				try {
					
					if (in != null)
						in.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		} else {
			
			result = FssClientUtil.getFileStream(filePath);
			
		}
		
		return result;
	}
	
	
	// ------------------------------------------------------
	
	
	/**
	 * 是否存在图片上传控件
	 * @return
	 */
	public static boolean existImgUpload() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String[] imageUploadIds = request.getParameterValues("imageUploadId");
		if ( imageUploadIds == null )
			return false;
		if ( imageUploadIds.length == 0 )
			return false;
		return true;
	}
	
	
	/**
	 * 是否存在图片上传控件
	 * @param imageUploadId 图片上传控件容器id
	 * @return
	 */
	public static boolean existImgUpload(String imageUploadId) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String[] imageUploadIds = request.getParameterValues("imageUploadId");
		if ( imageUploadIds == null )
			return false;
		for ( int i=0; i<imageUploadIds.length; i++ ) {
			if ( imageUploadId.equals( imageUploadIds[i] ) ) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 是否存在文件上传控件
	 * @return
	 */
	public static boolean existFileUpload() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String[] fileUploadIds = request.getParameterValues("fileUploadId");
		if ( fileUploadIds == null )
			return false;
		if ( fileUploadIds.length == 0 )
			return false;
		return true;
	}
	
	
	/**
	 * 是否存在文件上传控件
	 * @param fileUploadId 文件上传控件容器id
	 * @return
	 */
	public static boolean existFileUpload(String fileUploadId) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String[] fileUploadIds = request.getParameterValues("fileUploadId");
		if ( fileUploadIds == null )
			return false;
		for ( int i=0; i<fileUploadIds.length; i++ ) {
			if ( fileUploadId.equals( fileUploadIds[i] ) ) {
				return true;
			}
		}
		return false;
	}
	
	
}
