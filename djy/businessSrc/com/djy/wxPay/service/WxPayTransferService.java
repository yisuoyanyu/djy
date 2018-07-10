package com.djy.wxPay.service;

import com.djy.fin.model.FinTransfer;
import com.djy.wxPay.model.WxPayTransfer;
import com.frame.base.service.BaseService;

public interface WxPayTransferService extends BaseService<WxPayTransfer> {
	
	WxPayTransfer createByFinTransfer(FinTransfer finTransfer, String ip);
	
}
