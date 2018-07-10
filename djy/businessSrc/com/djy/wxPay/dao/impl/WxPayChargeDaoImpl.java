package com.djy.wxPay.dao.impl;

import org.springframework.stereotype.Service;

import com.djy.wxPay.dao.WxPayChargeDao;
import com.djy.wxPay.enumtype.WxPayChargeStatus;
import com.djy.wxPay.model.WxPayCharge;
import com.frame.base.dao.impl.BaseDaoImpl;

@Service("wxPayChargeDao")
public class WxPayChargeDaoImpl extends BaseDaoImpl<WxPayCharge> implements WxPayChargeDao {
	
	
	@Override
	public void dealPayFinish(WxPayCharge wxPayCharge) {
		
		String hql = "UPDATE " + WxPayCharge.class.getName() + " charge SET "
				+ " charge.status=" + WxPayChargeStatus.paying.getId()
				+ " WHERE charge.id=" + wxPayCharge.getId() 
				+ " and charge.status=" + WxPayChargeStatus.unpaid.getId();
		
		this.executeHql(hql);
		
	}
	
	
}
