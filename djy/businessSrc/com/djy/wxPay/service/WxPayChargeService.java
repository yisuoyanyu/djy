package com.djy.wxPay.service;

import com.djy.fin.model.FinCharge;
import com.djy.wxPay.model.WxPayCharge;
import com.djy.wxPay.model.WxPayNotice;
import com.frame.base.service.BaseService;

public interface WxPayChargeService extends BaseService<WxPayCharge> {
	
	WxPayCharge getByOutTradeNo(String outTradeNo);
	
	WxPayCharge getByNonceStr(String timeStamp, String nonceStr);
	
	WxPayCharge createByFinCharge(FinCharge finCharge);
	
	void dealPaySuccess(WxPayCharge wxPayCharge, WxPayNotice wxPayNotice);
	
	void dealPayFail(WxPayCharge wxPayCharge, WxPayNotice wxPayNotice);
	
	void dealPayFinish(WxPayCharge wxPayCharge);
	
}
