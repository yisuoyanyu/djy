package com.djy.wxPay.dao;

import com.djy.wxPay.model.WxPayCharge;
import com.frame.base.dao.BaseDao;

public interface WxPayChargeDao extends BaseDao<WxPayCharge> {
	
	void dealPayFinish(WxPayCharge wxPayCharge);
	
}
