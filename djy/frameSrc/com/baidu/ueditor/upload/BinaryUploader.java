package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {
	
	/**
	 * 
	 * @param request
	 * @param conf
	 * @return
	 * 2017.06.14 puzd 新增图片服务器判断
	 */
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);
			
			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			
			String fssApiSer = ConfigUtil.get("sys.fssClient.apiServer");
			
			if ( StringUtil.isEmpty( fssApiSer ) ) {	// 没有配置文件服务器
				
				// 文件相对路径（带格式）
				savePath = savePath + suffix;
				
				// 原文件 主文件名
				String originMainName = originFileName.substring(0,
						originFileName.length() - suffix.length());
				
				// 解析格式后的文件相对路径
				savePath = PathFormat.parse(savePath, originMainName);
				
				// 文件的物理路径
				// String physicalPath = (String) conf.get("rootPath") + savePath;
				String rootPath = (String) conf.get("rootPath");
				String webappsPath = rootPath.substring(0, rootPath.indexOf("webapps")+8);
				String physicalPath = webappsPath + savePath;
				
				InputStream is = fileStream.openStream();
				State storageState = StorageManager.saveFileByInputStream(is,
						physicalPath, maxSize);
				is.close();
	
				if (storageState.isSuccess()) {
					storageState.putInfo("url", PathFormat.format(savePath));
					storageState.putInfo("type", suffix);
					storageState.putInfo("original", originFileName);
				}
				
				return storageState;
				
			} else {								// 已配置文件服务器
				
				State storageState = StorageManager.saveFssByFileStream(fileStream, savePath, maxSize);
				
				if (storageState.isSuccess()) {
					storageState.putInfo("type", suffix);
					storageState.putInfo("original", originFileName);
				}
				return storageState;
			}
			
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
