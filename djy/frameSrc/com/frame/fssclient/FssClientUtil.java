package com.frame.fssclient;

import java.io.File;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.base.exception.FrontShowException;
import com.frame.fssclient.api.FssClientApi;
import com.frame.fssclient.api.json.FssFile;
import com.frame.fssclient.api.json.Result;

import net.sf.json.JSONObject;


public class FssClientUtil {

	private static Logger logger = LoggerFactory.getLogger(FssClientUtil.class);
	
	
	/**
	 * 新增文件
	 * @param file 要新增的文件
	 * @param filePath 文件保存路径（含文件的主文件名）。可以自定义保存路径和文件名格式，如：
	 *         /attachment/images/{yyyy}{mm}{dd}/{filename}
	 *         /attachment/ueditor/image/{yyyy}{mm}{dd}/{time}{rand:6}
	 *             {filename} 会替换成原文件名,配置这项需要注意中文乱码问题
	 *             {rand:6} 会替换成随机数,后面的数字是随机数的位数
	 *             {time} 会替换成时间戳
	 *             {yyyy} 会替换成四位年份
	 *             {yy} 会替换成两位年份
	 *             {mm} 会替换成两位月份
	 *             {dd} 会替换成两位日期
	 *             {hh} 会替换成两位小时
	 *             {ii} 会替换成两位分钟
	 *             {ss} 会替换成两位秒
	 *             非法字符 \ : * ? " < > | 
	 * @return
	 */
	public static FssFile putFile(File file, String filePath) {
		
		Result result = FssClientApi.putFile(file, filePath);
		
		if (result == null) {
			throw new FrontShowException( "请求失败！" );
		}
		
		if ( !result.getSuccess() ) {
			throw new FrontShowException( result.getMsg() );
		}
		
		JSONObject jsonObj = result.getDataJson();
		FssFile fssFile = FssFile.transfer(jsonObj);
		
		return fssFile;
	}
	
	
	public static boolean delFile(String[] filePaths) {
		
		Result result = FssClientApi.delFile(filePaths);
		
		if (result == null) {
			throw new FrontShowException( "请求失败！" );
		}
		
		return result.getSuccess();
	}
	
	public static boolean delFile(String filePath) {
		String[] filePaths = {filePath};
		return delFile(filePaths);
	}
	
	
	public static InputStream getFileStream(String filePath) {
		InputStream is = FssClientApi.getFileStream( filePath );
		return is;
	}
	
}
