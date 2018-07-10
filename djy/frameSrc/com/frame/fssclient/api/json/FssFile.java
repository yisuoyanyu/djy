package com.frame.fssclient.api.json;


import com.frame.fssclient.config.FssClientParameter;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

public class FssFile extends BaseJson {

	private String orgFileName;
	private String fileName;
	private String fileSuffix;
	private Long fileSize;
	private String filePath;
	
	
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	
	
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getUrl() {
		return FssClientParameter.apiServer + FssClientParameter.accessKeyId + filePath;
	}
	
	
	//--------------------------------------------------------------
	
	public static FssFile transfer(JSONObject jsonObj) {
		JsonConfig cfg = new JsonConfig();
		
		cfg.setPropertySetStrategy( new PropertyStrategyExt(PropertySetStrategy.DEFAULT) );
		cfg.setJavaIdentifierTransformer( new JavaIdentifierTransformerExt() );
		
		cfg.setRootClass( FssFile.class );
		
		FssFile fssFile = (FssFile) JSONObject.toBean(jsonObj, cfg);
		return fssFile;
	}
	
	
}
