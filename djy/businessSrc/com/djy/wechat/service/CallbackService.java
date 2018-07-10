package com.djy.wechat.service;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.djy.co.enumtype.CoPartnerStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.service.CoPartnerService;
import com.djy.user.enumtype.UserStatus;
import com.djy.user.enumtype.UserType;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.djy.wechat.enumtype.WechatTmplMsgStatus;
import com.djy.wechat.model.WechatNews;
import com.djy.wechat.model.WechatReply;
import com.djy.wechat.model.WechatTmplMsg;
import com.djy.wechat.model.WechatUser;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.utils.StringUtil;
import com.frame.wechat.api.json.UserInfo;
import com.frame.wechat.config.WechatParameter;
import com.frame.wechat.consts.MsgType;
import com.frame.wechat.consts.XmlResp;
import com.frame.wechat.api.MpApi;

/**
 * 回调业务处理
 */
@Component
public class CallbackService {

	private static Logger logger = LoggerFactory.getLogger( CallbackService.class );
	
	@Autowired
	private WechatReplyService replyService;
	
	@Autowired
	private WechatUserService wechatUserService;
	
	@Autowired
	private WechatNewsService newsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoPartnerService coPartnerService;
	
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	
	/**
	 * 微信功能处理
	 *
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public String handle(Map<String, String> reqMap) throws Exception {

		String msgType = reqMap.get("MsgType");

		// 针对不同类型的消息和事件进行处理
		
		if ( MsgType.TEXT.equals( msgType ) ) {	// 文本消息
			
			return handleText( reqMap );
			
		} else if ( MsgType.IMAGE.equals( msgType ) ) {	// 图片消息
			
			return handleImage( reqMap );
			
		} else if ( MsgType.VOICE.equals( msgType ) ) {	// 声音消息
			
			return handleVoice( reqMap );
			
		} else if ( MsgType.EVENT.equals( msgType ) ) {	// 基础事件推送
			
			String event = reqMap.get("Event");
			
			if ( MsgType.Event.SUBSCRIBE.equals( event ) ) {	// 关注公众号
				
				return handleSubscribe( reqMap );
				
			} else if ( MsgType.Event.UNSUBSCRIBE.equals( event ) ) {	// 取消关注
				
				return handleUnsubscribe( reqMap );
				
			} else if ( MsgType.Event.CLICK.equals( event ) ) {	// 菜单点击事件
				
				return handleClick( reqMap );
				
			} else if ( MsgType.Event.SCAN.equals( event ) ) {	// 用户扫描事件
				
				return handleScan( reqMap );
				
			} else if ( MsgType.Event.TEMPLATESENDJOBFINISH.equals( event ) ) {
				
				handleTemplateSendJobFinish( reqMap );
				
			}
		}
		
		// 未处理的情况返回空字符串
		return "";
	}
	
	
	//------------------------------------------------------------
	
	
	/**
	 * 处理文本信息
	 * @param reqMap
	 * @return
	 */
	private String handleText(Map<String, String> reqMap) {
		
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		
		String content = "";
		// 可以在此处进行关键字自动回复
		HqlFilter hqlFilter = new HqlFilter();
		WechatReply reply = replyService.getByContent(reqMap.get("Content"));
		if (reply != null) {
			if ("txt".equals(reply.getType())) {
				content = reply.getOutput();
			} else if ("img".equals(reply.getType())) {
				return XmlResp.buildNews(fromUser, toUser, reply.getCount().toString(), reply.getOutput(),
						reply.getDescription(), reply.getPicurl(), reply.getUrl());
			}
		} else {
			hqlFilter.addFilter("title", reqMap.get("Content"), SqlOperator.like);
			List<WechatNews> list = newsService.findByFilter(hqlFilter);
			WechatNews lnews;
			if (list.size() > 0) {
				String hNews = XmlResp.buildHNews(fromUser, toUser, String.valueOf(list.size()));
				String cNews = "";
				for (int i = 0; i < list.size(); i++) {
					lnews = list.get(i);
					cNews = cNews + XmlResp.buildCNews(lnews.getTitle(), lnews.getDescription(), lnews.getPicurl(),
							lnews.getUrl());
				}
				return hNews + cNews + "</Articles>" + "</xml>";
			} else {
				return "";
			}
		}
		return XmlResp.buildText(fromUser, toUser, content);
		
	}
	
	
	/**
	 * 处理图片消息
	 * @param reqMap
	 * @return
	 */
	private String handleImage(Map<String, String> reqMap) {
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		return XmlResp.buildText(fromUser, toUser, "\ue11b主人，这张美图我暂时不知道怎么处理呢\uE11b");
	}
	
	
	/**
	 * 处理声音消息
	 * @param reqMap
	 * @return
	 */
	private String handleVoice(Map<String, String> reqMap) {
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		return XmlResp.buildText(fromUser, toUser, "\uE129主人，这段美妙的声音我暂时不知道怎么处理呢\ue129");
	}
	
	//------------------------------------------------------------
	
	/**
	 * 处理关注公众号
	 * @param reqMap
	 * @return
	 */
	private String handleSubscribe(Map<String, String> reqMap) {
		
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		String eventKeys = reqMap.get("EventKey");
		if (eventKeys.startsWith("qrscene_")) {
			 eventKeys = eventKeys.substring(8);
		}
		
		UserInfo userInfo = MpApi.getUserInfo(fromUser);
		
		WechatUser wechatUser = wechatUserService.getByOpenID(userInfo.getOpenid());
		String eventKey = reqMap.get("EventKey");//永久型二维码；合作商家的二维码信息是“qrscene_coId_75”；；门店店员的二维码信息是“qrscene_syId_75”
		
		String sceneStr = null;
		if (eventKey.startsWith("qrscene_")) {
			sceneStr = eventKey.substring(8);	//获取二维码中参数的值
		}
		
		if (wechatUser == null) {//表示为首次关注
			
			wechatUserService.createByUserInfo(userInfo, sceneStr);
			
		} else {//表示再次关注【存在角色转换的情况】
			
			wechatUserService.refreshByUserInfo(wechatUser, userInfo, sceneStr);
			
		}
		String welcomeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatParameter.appID 
				+ "&redirect_uri=" + WechatParameter.serverUrl + "copartner%2FOauth2.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		
		// 回复欢迎语
//		return XmlResp.buildNews(toUser, fromUser, "1", "欢迎关注互联网加油平台--油惠站", "初次关注“油惠站”送一张折扣券；每分享一个朋友关注送一张折扣券；一次加油送一张券，多分享多省钱。", WechatParameter.serverUrl+"copartner/img/welcome.jpg", welcomeUrl);
//		
			String hNews = XmlResp.buildHNews(fromUser, toUser, String.valueOf(1));
			String cNews = "";
			for (int i = 0; i < 1; i++) {
				cNews = cNews + XmlResp.buildCNews("告诉加油员用油惠站付款！", "首次关注“油惠站”即可领一张折扣券，每分享推广一位朋友关注“油惠站”就可多领一张折扣券，多分享多省钱！", WechatParameter.serverUrl+"copartner/img/welcome.jpg", 
						welcomeUrl);
			}
			return hNews + cNews + "</Articles>" + "</xml>";
		
	}
	
	
	/**
	 * 处理取消关注公众号
	 * @param reqMap
	 * @return
	 */
	private String handleUnsubscribe(Map<String, String> reqMap) {
		
		String fromUser = reqMap.get("FromUserName");
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("openId", fromUser);
		WechatUser wechatUser = wechatUserService.getByFilter(filter);
		
		if ( wechatUser == null )
			return "";
		
		wechatUser.setSubscribe(0);// 取消订阅
		wechatUserService.update(wechatUser); // 用户取消关注时就更改微信用户订阅状态。

		User user = wechatUser.getUser();
		if ( user.getType() == UserType.customer.getId() 
				|| user.getType() == UserType.customer.getId() ) {
			user.setStatus(UserStatus.freeze.getId()); // 设置商家顾客或者普通顾客状态为不可用
			userService.update(user);
		}
		if ( user.getType() == UserType.copartner.getId() ) {//表示它现在是一个合作商家
			CoPartner coPartner = user.getCoPartner();
			coPartner.setStatus(CoPartnerStatus.freeze.getId());//设置合作商家状态为不可用状态
			coPartnerService.update(coPartner);
		}
		
		return "";
	}
	
	//------------------------------------------------------------
	
	/**
	 * 处理点击公众号菜单
	 * @param reqMap
	 * @return
	 */
	private String handleClick(Map<String, String> reqMap) {
		
		try {
			
			// 根据key值判断点击的哪个菜单
			String eventKey = reqMap.get("EventKey");
			
			if ("PHONE".equals(eventKey)) {		// 联系电话
				
				return handleClickPhone(reqMap);
				
			} else if ("ADDRESS".equals(eventKey)) {			// 联系地址
				
				return handleClickAddress(reqMap);
				
			} else if ("EXPECT".equals(eventKey)) {		// 敬请期待
				
				return handleClickExpect(reqMap);
				
			}
			
			
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}
	
	
	
	/**
	 * 处理点击公众号菜单“联系电话”
	 * @param reqMap
	 * @return
	 */
	private String handleClickPhone(Map<String, String> reqMap){
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		String replay = "联系电话：4006651970";
		return XmlResp.buildText(fromUser, toUser, replay);
	}
	
	
	/**
	 * 处理点击公众号菜单“联系地址”
	 * @param reqMap
	 * @return
	 */
	private String handleClickAddress(Map<String, String> reqMap) {
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		String replay = "联系地址：北京市朝阳区望京北路叶青大厦C座C506";
		return XmlResp.buildText(fromUser, toUser, replay);
	}
	
	
	/**
	 * 前期处理“敬请期待”
	 * @param reqMap
	 * @return
	 */
	private String handleClickExpect(Map<String, String> reqMap) {
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		String replay = "敬请期待";
		return XmlResp.buildText(fromUser, toUser, replay);
	}
	
	//------------------------------------------------------------
	
	/**
	 * 处理微信扫描事件
	 * @param reqMap
	 * @return
	 */
	private String handleScan(Map<String, String> reqMap) {
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		String scanValue = reqMap.get("EventKey");
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("openId", fromUser);
		WechatUser wechatUser = wechatUserService.getByFilter(filter);
		if ( wechatUser == null ) {
			UserInfo userInfo = MpApi.getUserInfo( fromUser );
			wechatUser = wechatUserService.createByUserInfo(userInfo, null);
		}
		
		User user = wechatUser.getUser();
		
		// 普通用户通过扫描事件变成合作商家
		if ( ! StringUtil.isBlank( scanValue ) 
				&& scanValue.startsWith("COPARTNER") 
				&& user.getType() == UserType.customer.getId() ) {
			
			user.setType( UserType.copartner.getId() );
			userService.update( user );
			
			if (user.getCoPartner() == null)
				coPartnerService.createByUser( user );
			
			String zzhkUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatParameter.appID 
					+ "&redirect_uri=" + WechatParameter.serverUrl + "point%2FOauth2.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
			
			String replay = "恭喜您已经成为油惠站的合作商家加油站！请您"
					+ "<a href=\"" + zzhkUrl + "\">进入系统</a>" + "完善您的个人商家信息！";
			
			return XmlResp.buildText(fromUser, toUser, replay);
		
		}else if ( ! StringUtil.isBlank( scanValue )&& scanValue.startsWith("U") ) {//表示用户直接在外面扫描了商家的付款二维码
			User copUser = userService.getUserBySpreadCode(scanValue);
			if (null != copUser && copUser.getType() == UserType.copartner.getId()) {//表示为合作商家正确的二维码
				if (copUser.getCoPartner().getStatus() == CoPartnerStatus.normal.getId()) {
					String copUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatParameter.appID 
							+ "&redirect_uri=" + WechatParameter.serverUrl + "copartner%2FOauth2.do?copId="+copUser.getCoPartner().getId()+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
					String replay = "您已扫描"+copUser.getCoPartner().getName()+"商家二维码，点击"
							+ "<a href=\"" + copUrl + "\">进入商家</a>";
					return XmlResp.buildText(fromUser, toUser, replay);
				}else {
					return "";
				}
				
			}else {
				return XmlResp.buildText(fromUser, toUser, "请您扫描油惠站合作油站提供的二维码");
			}
		}else {
			return "";
		}
	}
	
	//------------------------------------------------------------
	
	private void handleTemplateSendJobFinish(Map<String, String> reqMap) {
		String fromUserName = reqMap.get("FromUserName");
		String msgId = reqMap.get("MsgID");
		WechatTmplMsg wechatTmplMsg = wechatTmplMsgService.getByMsgId(fromUserName, msgId);
		if ( wechatTmplMsg != null ) {
			String status = reqMap.get("Status");
			if ( "success".equals( status ) ) {
				wechatTmplMsg.setStatus( WechatTmplMsgStatus.sendSuccess.getId() );
			} else if ( status != null && status.startsWith("failed:") ) {
				wechatTmplMsg.setStatus( WechatTmplMsgStatus.sendFailed.getId() );
				wechatTmplMsg.setErrMsg( status.substring( 7 ) );
			}
			wechatTmplMsgService.saveOrUpdate( wechatTmplMsg );
		}
	}
	
	//------------------------------------------------------------
	
}
