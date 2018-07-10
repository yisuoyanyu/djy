package com.djy.wechat.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.wechat.enumtype.WechatTmplMsgStatus;
import com.djy.wechat.model.WechatTmplMsg;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatMsgTmplService;
import com.djy.wechat.service.WechatTmplMsgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.StringUtil;
import com.frame.wechat.api.MpApi;
import com.frame.wechat.api.json.TmplMsg;

import net.sf.json.JSONObject;


@Service("wechatTmplMsgService")
public class WechatTmplMsgSerivceImpl extends BaseServiceImpl<WechatTmplMsg> implements WechatTmplMsgService {
	
	@Autowired
	private WechatMsgTmplService wechatMsgTmplService;
	
	
	
	@Override
	public WechatTmplMsg getByMsgId(String toUser, String msgId) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("toUser", toUser);
		filter.addFilter("msgId", msgId);
		return this.getByFilter( filter );
	}
	
	
	@Override
	public WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content) {
		try {
			WechatTmplMsg wechatTmplMsg = sendTmplMsg(toUser, tmplCode, content, null, null, null);
			return wechatTmplMsg;
		} catch( Exception e ) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	@Override
	public WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content, String url) {
		try {
			WechatTmplMsg wechatTmplMsg = sendTmplMsg(toUser, tmplCode, content, url, null, null);
			return wechatTmplMsg;
		} catch( Exception e ) {
			return null;
		}
	}
	
	
	@Override
	public WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content, String miniProgramAppid, String miniProgramPagePath) {
		try {
			WechatTmplMsg wechatTmplMsg = sendTmplMsg(toUser, tmplCode, content, null, miniProgramAppid, miniProgramPagePath);
			return wechatTmplMsg;
		} catch( Exception e ) {
			return null;
		}
	}
	
	
	private WechatTmplMsg sendTmplMsg(WechatUser toUser, String tmplCode, Map<String, Object> content, String url, String miniProgramAppid, String miniProgramPagePath) throws Exception {
		
		String templateId = wechatMsgTmplService.getTemplateIdByCode( tmplCode );
		
		if ( StringUtil.isBlank( templateId ) ) {
			throw new Exception("找不到微信公众号消息模板");
		}
		
		WechatTmplMsg wechatTmplMsg = new WechatTmplMsg();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("touser", toUser.getOpenid());
		wechatTmplMsg.setToUser( toUser.getOpenid() );
		
		map.put("template_id", templateId);
		wechatTmplMsg.setTemplateId( templateId );
		
		if ( ! StringUtil.isBlank( url ) ) {
			map.put("url", url);
			wechatTmplMsg.setUrl( url );
		}
		
		if ( StringUtil.isBlank( miniProgramAppid ) && !StringUtil.isBlank( miniProgramPagePath ) 
				|| !StringUtil.isBlank( miniProgramAppid ) && StringUtil.isBlank( miniProgramPagePath ) ) {
			throw new Exception("如需跳到小程序，miniProgramAppid 和 miniProgramPagePath 都不能为空");
		} else if ( !StringUtil.isBlank( miniProgramAppid ) && !StringUtil.isBlank( miniProgramPagePath ) ) {
			Map<String, String> miniProgram = new HashMap<>();
			miniProgram.put("appid", miniProgramAppid);
			miniProgram.put("pagepath", miniProgramPagePath);
			map.put("miniProgram", miniProgram);
			wechatTmplMsg.setMiniProgramAppid( miniProgramAppid );
			wechatTmplMsg.setMiniProgramPagePath( miniProgramPagePath );
		}
		
		Map<String, Object> data = new HashMap<>();
		for (String key : content.keySet()) {
			if ( content.get(key) instanceof Map ) {
				data.put(key, content.get(key));
			} else {
				Map<String, String> valueMap = new HashMap<>();
				Object value = content.get(key);
				valueMap.put("value", value.toString());
				data.put(key, valueMap);
			}
		}
		map.put("data", data);
		
		wechatTmplMsg.setData( JSONObject.fromObject( map ).toString() );
		
		TmplMsg tmplMsg = MpApi.templateSend( map );
		
		if ( tmplMsg.getErrcode() == 0 ) {
			
			String msgId = tmplMsg.getMsgid();
			
			wechatTmplMsg.setMsgId( msgId );
			wechatTmplMsg.setStatus( WechatTmplMsgStatus.sending.getId() );
			
		} else {
			
			wechatTmplMsg.setStatus( WechatTmplMsgStatus.sendFail.getId() );
			wechatTmplMsg.setErrMsg( tmplMsg.getErrmsg() );
			
		}
		wechatTmplMsg.setInsertTime( new Date() );
		this.save( wechatTmplMsg );
		
		return wechatTmplMsg;
	}
	
}
