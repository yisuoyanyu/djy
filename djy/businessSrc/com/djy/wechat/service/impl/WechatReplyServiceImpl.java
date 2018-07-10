package com.djy.wechat.service.impl;

import com.frame.base.service.impl.BaseServiceImpl;
import com.djy.wechat.model.WechatReply;
import com.djy.wechat.service.WechatReplyService;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/7/9.
 */
@Service("wechatReplyService")
public class WechatReplyServiceImpl extends BaseServiceImpl<WechatReply> implements WechatReplyService {

	@Override
	public WechatReply getByContent(String content) {
		WechatReply retWechatReply = null;
		List<WechatReply> wechatReplies = find();
		for ( WechatReply wechatReply : wechatReplies) {
			if (content.contains( wechatReply.getInput() )) {
				retWechatReply = wechatReply;
			}
		}
		return retWechatReply;
	}
 
}
