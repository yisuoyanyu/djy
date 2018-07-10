package com.frame.fssclient.config;

import com.frame.base.utils.ConfigUtil;

public class FssClientParameter {
	
	public static String apiServer = ConfigUtil.get("sys.fssClient.apiServer");
	public static String accessKeyId = ConfigUtil.get("sys.fssClient.accessKeyId");
	public static String accessKeySecret = ConfigUtil.get("sys.fssClient.accessKeySecret");
	
}
