package com.frame.wechat.api;

import com.frame.wechat.api.json.UserInfo;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.wechat.api.json.AccessToken;
import com.frame.wechat.api.json.Article;
import com.frame.wechat.api.json.JsApiTicket;
import com.frame.wechat.api.json.QrcodeTicket;
import com.frame.wechat.api.json.TmplMsg;
import com.frame.wechat.api.utils.HttpUtil;
import com.frame.wechat.config.WechatParameter;
import com.frame.wechat.consts.MsgType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 公众平台API 设备专用API定义在DeviceApi
 */
public class MpApi {

	private static Logger logger = LoggerFactory.getLogger(MpApi.class);
	
	// access_token接口地址
	private static final String GetAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
			+ "&appid=" + WechatParameter.appID + "&secret=" + WechatParameter.appsecret;
	
	// 用户信息接口地址
	private static final String UserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&lang=zh_CN";
		
	// jsapi_ticket接口地址
	private static final String GetJsApiTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"; 
	
	// 客服消息接口地址
	private static final String CustomSendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	// 模板消息接口地址
	private static final String TemplateSendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	// 公众号菜单接口地址
	private static final String CreateMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String QueryMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	private static final String DeleteMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	// 带参数二维码ticket接口地址
	private static final String GetQrcodeTicket = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	
	// 二维码地址
	private static final String ShowQrcode = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	
	//--------------------------------------------------------------
	
	
	/**
	 * 获取访问凭证
	 * <p>
	 * 正常情况下access_token有效期为7200秒，重复获取将导致上次获取的access_token失效。
	 * 由于获取access_token的api调用次数非常有限，需要全局存储与更新access_token
	 * <br/>
	 * 文档位置：基础支持->获取access token
	 */
	public static AccessToken getAccessToken() {
		String resultContent = HttpUtil.executeGet(GetAccessTokenUrl);
		return AccessToken.fromJson( resultContent );
	}	
	
	
	//--------------------------------------------------------------
	
	
	/**
	 * 获取用户信息（包括微信名，性别，头像，注册日期等信息）
	 */
	public static UserInfo getUserInfo(String openId) {
		String resultContent = HttpUtil.doGet(UserInfoUrl + "&openid=" + openId);
		return UserInfo.fromJson( resultContent );
	}
	
	
	/**
	 * 获取JsApiTicket
	 */
	public static JsApiTicket getJsApiTicket() {
		String resultContent = HttpUtil.doGet(GetJsApiTicket);
		return JsApiTicket.fromJson( resultContent );
	}
	
	
	//--------------------------------------------------------------
	
	
	/**
	 * 发送客服消息 <br/>
	 * 文档位置：发送消息->发送客服消息
	 */
	public static void customSend(String body) {
		HttpUtil.doPost(CustomSendUrl, body);
	}
	
	
	/**
	 * 发客服文本消息
	 */
	public static void customSendText(String touser, String content) {
		JSONObject json = new JSONObject();
		json.put("touser", touser);
		json.put("msgtype", MsgType.TEXT);
		JSONObject text = new JSONObject();
		text.put("content", content);
		json.put("text", text);
		customSend( json.toString() );
	}
	
	
	/**
	 * 发客服图文消息
	 */
	public static void customSendNews(String touser, List<Article> articles){
		JSONObject json = new JSONObject();
		json.put("touser", touser);
		json.put("msgtype", MsgType.NEWS);
		
		JSONObject news = new JSONObject();
		JSONArray articleArray = new JSONArray();
		for (int i=0; i<articles.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("title", articles.get(i).getTitle());
			jsonObject.put("description", articles.get(i).getDescription());
			jsonObject.put("url", articles.get(i).getUrl());
			jsonObject.put("picurl", articles.get(i).getPicurl());
			articleArray.add(jsonObject);
		}
		news.put("articles", articles);
		json.put("news", news);
		
        customSend( json.toString() );
    }
	
	
	//--------------------------------------------------------------
	
	
	/**
	 * 发送模板消息
	 * @param body
	 * @return
	 */
	public static TmplMsg templateSend(String body) {
		String resultContent = HttpUtil.doPost(TemplateSendUrl, body);
		return TmplMsg.fromJson( resultContent );
	}
	
	
	/**
	 * 发送模板消息
	 * @param jsonObject
	 *     {
	 *         "touser" : （必填）接收者openid, 
	 *         "template_id" : （必填）模板ID, 
	 *         "url" : （可选）模板跳转链接, 
	 *         "miniprogram" : {            // （可选）跳小程序所需数据 
	 *             "appid" : （必填）所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）, 
	 *             "pagepath" : （必填）所需跳转到小程序的具体页面路径，支持带参数（示例index?foo=bar）
	 *         }, 
	 *         "data" : {
	 *             "key1" : {
	 *                 "value" : （必填）模板内容文字, 
	 *                 "color" : （可选）模板内容字体颜色，不填默认为黑色
	 *             }, 
	 *             ……
	 *         }
	 *     }
	 * @return
	 */
	public static TmplMsg templateSend(JSONObject jsonObject) {
		return templateSend( jsonObject.toString() );
	}
	
	
	/**
	 * 发送模板消息
	 * @param map
	 *     {
	 *         "touser" : （必填）接收者openid, 
	 *         "template_id" : （必填）模板ID, 
	 *         "url" : （可选）模板跳转链接, 
	 *         "miniprogram" : {            // （可选）跳小程序所需数据 
	 *             "appid" : （必填）所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）, 
	 *             "pagepath" : （必填）所需跳转到小程序的具体页面路径，支持带参数（示例index?foo=bar）
	 *         }, 
	 *         "data" : {
	 *             "key1" : {
	 *                 "value" : （必填）模板内容文字, 
	 *                 "color" : （可选）模板内容字体颜色，不填默认为黑色
	 *             }, 
	 *             ……
	 *         }
	 *     }
	 * @return
	 */
	public static TmplMsg templateSend(Map<String, Object> map) {
		JSONObject jsonObject = JSONObject.fromObject( map );
		// System.out.println( jsonObject.toString() );
		return templateSend( jsonObject.toString() );
	}
	
	
	//--------------------------------------------------------------
    
	
	/**
	 * 创建自定义菜单<p>
	 * 文档位置：自定义菜单->自定义菜单创建接口
	 */
	public static String menuCreate(String body) {
		return HttpUtil.doPost(CreateMenuUrl, body);
	}
	
	
	/**
	 * 查询自定义菜单<p>
	 * 文档位置：自定义菜单->自定义菜单查询接口
	 */
	public static String menuQuery() {
		return HttpUtil.doGet( QueryMenuUrl );
	}
	
	
	/**
	 * 删除自定义菜单<p>
	 * 文档位置：自定义菜单->自定义菜单删除接口
	 */
	public static String menuDelete() {
		return HttpUtil.doGet( DeleteMenuUrl );
	}
	
	
	//--------------------------------------------------------------
	
	
	/**
	 * 获取带参数永久二维码Ticket
	 */
	public static QrcodeTicket getQrLimitStrSceneTicket(String sceneStr) {
		JSONObject json = new JSONObject();
		json.put("action_name", "QR_LIMIT_STR_SCENE");
		
		JSONObject scene = new JSONObject();
		scene.put("scene_str", sceneStr);
		
		JSONObject actionInfo = new JSONObject();
		actionInfo.put("scene", scene);
		
		json.put("action_info", actionInfo);
		
		String resultContent = HttpUtil.doPost(GetQrcodeTicket, json.toString());
		return QrcodeTicket.fromJson( resultContent );
	}
	
	
	/**
	 * 获取二维码图片URL
	 * @param sceneStr
	 * @return
	 */
	public static String getQrLimitStrSceneUrl(String sceneStr) {
		QrcodeTicket qrcodeTicket = getQrLimitStrSceneTicket(sceneStr);
		String ticket = qrcodeTicket.getTicket();
		try {
			ticket = URLEncoder.encode(ticket, "UTF-8");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		String url = ShowQrcode.replace("TICKET", ticket);
		return url;
	}
	
	
	/**
	 * 获取二维码url
	 * @param sceneStr
	 * @return
	 */
	public static String getSceneUrl(String ticket) {
		try {
			ticket = URLEncoder.encode(ticket, "UTF-8");
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		String url = ShowQrcode.replace("TICKET", ticket);
		return url;
	}
	
	
	//--------------------------------------------------------------
	
}
