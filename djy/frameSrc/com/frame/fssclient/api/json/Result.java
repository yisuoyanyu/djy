package com.frame.fssclient.api.json;


import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

public class Result extends BaseJson {

	private Boolean success;
	private String msg;
	private Object data;
	
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object result) {
		this.data = result;
	}
	public JSONObject getDataJson() {
		return JSONObject.fromObject(data);
	}
	
	//--------------------------------------------------------------
	
	
	public static Result transfer(String json) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		
		JsonConfig cfg = new JsonConfig();
		
		cfg.setPropertySetStrategy( new PropertyStrategyExt(PropertySetStrategy.DEFAULT) );
		cfg.setJavaIdentifierTransformer( new JavaIdentifierTransformerExt() );
		
		cfg.setRootClass( Result.class );
		
		Result result = (Result) JSONObject.toBean(jsonObj, cfg);
		return result;
	}
	
}
