package com.frame.wechat.consts;

/**
 * Xml协议响应
 */
public class XmlResp {

	/**
	 * 文本消息响应格式
	 */
	public static final String TEXT = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>").append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>").append("<MsgType><![CDATA[%s]]></MsgType>")
			.append("<Content><![CDATA[%s]]></Content>").append("</xml>").toString();

	/**
	 * 构造文本消息响应
	 */
	public static final String buildText(String toUser, String fromUser, String content) {
		return String.format(TEXT, toUser, fromUser, time(), MsgType.TEXT, content);
	}
	
	
	/**
	 * 图片消息响应格式
	 */
	public static final String IMAGE = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>").append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>").append("<MsgType><![CDATA[%s]]></MsgType>").append("<Image>")
			.append("<MediaId><![CDATA[%s]]></MediaId>").append("</Image>").append("</xml>").toString();

	/**
	 * 构造图片消息响应
	 */
	public static final String buildImage(String toUser, String fromUser, String content) {
		return String.format(IMAGE, toUser, fromUser, time(), MsgType.IMAGE, content);
	}
	
	
	/**
	 * 排行榜消息响应格式
	 */
	public static final String RANK = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>").append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>").append("<MsgType><![CDATA[hardware]]></MsgType>")
			.append("<HardWare>").append("<MessageView><![CDATA[myrank]]></MessageView>")
			.append("<MessageAction><![CDATA[ranklist]]></MessageAction>").append("</HardWare>")
			.append("<FuncFlag>0</FuncFlag>").append("</xml>").toString();

	/**
	 * 构造排行榜消息响应
	 */
	public static final String buildRank(String toUser, String fromUser) {
		return String.format(RANK, toUser, fromUser, time());
	}
	
	
	/**
	 * News消息响应格式
	 */
	public static final String NEWS = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>")
			.append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>")
			.append("<MsgType><![CDATA[%s]]></MsgType>")
			.append("<ArticleCount><![CDATA[%s]]></ArticleCount>")
			.append("<Articles>")
			.append("<item>")
			.append("<Title><![CDATA[%s]]></Title>")
			.append("<Description><![CDATA[%s]]></Description>")
			.append("<PicUrl><![CDATA[%s]]></PicUrl>")
			.append("<Url><![CDATA[%s]]></Url>").append("</item>")
			.append("</Articles>")
			.append("</xml>").toString();

	/**
	 * 构造News消息响应
	 */
	public static final String buildNews(String toUser, String fromUser, 
			String count, String title, String description, String purl, String url) {
		return String.format(NEWS, toUser, fromUser, time(), MsgType.NEWS, count, title, description, purl, url);
	}
	
	
	/*
	 * HNews消息头的格式
	 */
	public static final String HNEWS = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>")
			.append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>")
			.append("<MsgType><![CDATA[%s]]></MsgType>")
			.append("<ArticleCount><![CDATA[%s]]></ArticleCount>")
			.append("<Articles>").toString();;

	/*
	 * 构造HNEWS消息头响应 
	 */
	public static final String buildHNews(String toUser, String fromUser, String count) {
		return String.format(HNEWS, toUser, fromUser, time(), MsgType.NEWS, count);
	}
	
	
	/*
	 * CNEWS消息内容的格式
	 */
	public static final String CNEWS = new StringBuilder().append("<item>").append("<Title><![CDATA[%s]]></Title>")
			.append("<Description><![CDATA[%s]]></Description>").append("<PicUrl><![CDATA[%s]]></PicUrl>")
			.append("<Url><![CDATA[%s]]></Url>").append("</item>").toString();

	/**
	 * 构造CNews消息响应
	 */
	public static final String buildCNews(String title, String description, String purl, String url) {
		return String.format(CNEWS, title, description, purl, url);
	}
	
	
	/**
	 * 设备消息响应格式
	 */
	public static final String DEVICE_TEXT = new StringBuilder().append("<xml>")
			.append("<ToUserName><![CDATA[%s]]></ToUserName>")
			.append("<FromUserName><![CDATA[%s]]></FromUserName>")
			.append("<CreateTime>%s</CreateTime>")
			.append("<MsgType><![CDATA[%s]]></MsgType>")
			.append("<DeviceType><![CDATA[%s]]></DeviceType>")
			.append("<DeviceID><![CDATA[%s]]></DeviceID>")
			.append("<SessionID>%s</SessionID>")
			.append("<Content><![CDATA[%s]]></Content>")
			.append("</xml>").toString();

	/**
	 * 构造设备消息响应
	 */
	public static final String buildDeviceText(String fromUser, String toUser, 
			String deviceType, String deviceID, String content, String sessionID) {
		return String.format(DEVICE_TEXT, fromUser, toUser, time(), 
				MsgType.DEVICE_TEXT, deviceType, deviceID, sessionID, content);
	}
	
	
	/**
	 * 秒级时间戳
	 */
	private static String time() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static void main(String[] args) {
		System.out.println(buildText("", "", ""));
		System.out.println(buildDeviceText("", "", "", "", "", ""));
	}
	
}
