package com.frame.fssclient.api;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.base.exception.FrontShowException;
import com.frame.base.utils.StringUtil;
import com.frame.fssclient.api.json.Result;
import com.frame.fssclient.api.utils.HttpUtil;
import com.frame.fssclient.config.FssClientParameter;

public class FssClientApi {

	private static Logger logger = LoggerFactory.getLogger(FssClientApi.class);
	
	
	public static Result putFile(File file, String filePath) {
		
		String url = FssClientParameter.apiServer + "api/putFile.json";
		
		Map<String, Object> param = new HashMap<>();
		param.put("accessKeyId", FssClientParameter.accessKeyId);
		param.put("accessKeySecret", FssClientParameter.accessKeySecret);
		param.put("file", file);
		param.put("filePath", filePath);
		
		String resultStr;
		int i = 3;
		do {
			if ( i < 3 ) {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			resultStr = HttpUtil.doPost(url, param, 3, "utf-8");
			i--;
		} while( StringUtil.isEmpty(resultStr) && i > 0 );
		
		if ( StringUtil.isEmpty(resultStr) ) {
			throw new FrontShowException( "请求失败！" );
		}
		
		Result result = Result.transfer(resultStr);
		
		return result;
	}
	
	
	public static Result delFile(String[] filePaths) {
		
		String url = FssClientParameter.apiServer + "api/delFile.json";
		
		Map<String, Object> param = new HashMap<>();
		param.put("accessKeyId", FssClientParameter.accessKeyId);
		param.put("accessKeySecret", FssClientParameter.accessKeySecret);
		param.put("filePaths", filePaths);
		
		String resultStr;
		int i = 3;
		do {
			if ( i < 3 ) {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			resultStr = HttpUtil.doPost(url, param, "utf-8");
			i--;
		} while( StringUtil.isEmpty(resultStr) && i > 0 );
		
		if ( StringUtil.isEmpty(resultStr) ) {
			throw new FrontShowException( "请求失败！" );
		}
		
		Result result = Result.transfer(resultStr);
		
		return result;
	}
	
	
	public static InputStream getFileStream(String filePath) {
		String url = FssClientParameter.apiServer + FssClientParameter.accessKeyId + filePath;
		InputStream is = HttpUtil.doGetStream(url, null, "utf-8");
		return is;
	}
	
	
}
