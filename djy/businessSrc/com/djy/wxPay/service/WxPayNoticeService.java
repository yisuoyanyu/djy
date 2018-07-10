package com.djy.wxPay.service;

import java.util.Map;

import com.djy.wxPay.model.WxPayNotice;
import com.frame.base.service.BaseService;

public interface WxPayNoticeService extends BaseService<WxPayNotice> {
	
	WxPayNotice creatByResult(String resultXml, Map<String, String> resultMap);
	
	void dealWxPayNotice(WxPayNotice wxPayNotice);
	
}
