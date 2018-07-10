package com.djy.wxPay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.model.FinCharge;
import com.djy.fin.service.FinChargeService;
import com.djy.wxPay.dao.WxPayChargeDao;
import com.djy.wxPay.enumtype.WxPayChargeStatus;
import com.djy.wxPay.model.WxPayCharge;
import com.djy.wxPay.model.WxPayNotice;
import com.djy.wxPay.service.WxPayChargeService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.wxpay.service.WxPayService;


@Service("wxPayChargeService")
public class WxPayChargeServiceImpl extends BaseServiceImpl<WxPayCharge> implements WxPayChargeService {
	
	@Autowired
	private WxPayService wxPayService;
	
	@Autowired
	private FinChargeService finChargeService;
	
	@Autowired
	private WxPayChargeDao wxPayChargeDao;
	
	
	@Override
	public WxPayCharge getByOutTradeNo(String outTradeNo) {
		
		HqlFilter filter = new HqlFilter();
		filter.addFilter("outTradeNo", outTradeNo);
		
		return this.getByFilter( filter );
		
	}
	
	
	@Override
	public WxPayCharge getByNonceStr(String timeStamp, String nonceStr) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("timeStamp", timeStamp);
		hqlFilter.addFilter("nonceStr", nonceStr);
		return this.getByFilter(hqlFilter);
	}
	
	
	@Override
	public WxPayCharge createByFinCharge(FinCharge finCharge) {
		
		try {
			
			String body = finCharge.getSubject();
			String detail = finCharge.getBody();
			String outTradeNo = finCharge.getOrderNo();
			Integer totalFee = new Double(finCharge.getAmount() * 100).intValue();
			String spbillCreateIp = finCharge.getClientIp();
			String openid = finCharge.getUser().getWechatUser().getOpenid();
			
			// 调用支付API
			Map<String, String> data = new HashMap<>();
			data.put("body", body);
			if ( detail != null )
				data.put("detail", detail);
			data.put("out_trade_no", outTradeNo);
			
			data.put("total_fee", totalFee.toString());
			// data.put("total_fee", "1");		// 测试环境暂时支付0.01元
			
			data.put("spbill_create_ip", spbillCreateIp);
			if ( finCharge.getChannel() == FinChannel.wxPub.getId() ) {
				data.put("trade_type", "JSAPI");
				data.put("openid", openid);
			}
			
			Map<String, String> result = wxPayService.unifiedOrder( data );
			
			String appid = result.get("appid");
			String mchId = result.get("mch_id");
			String deviceInfo = result.get("device_info");
			String resultCode = result.get("result_code");
			String errCode = result.get("err_code");
			String errCodeDes = result.get("err_code_des");
			
			String tradeType = result.get("trade_type");
			String prepayId = result.get("prepay_id");
			String codeUrl = result.get("code_url");
			
			WxPayCharge wxPayCharge = new WxPayCharge();
			wxPayCharge.setFinCharge( finCharge );
			wxPayCharge.setAppid( appid );
			wxPayCharge.setMchId( mchId );
			wxPayCharge.setDeviceInfo( deviceInfo );
			wxPayCharge.setBody( body );
			wxPayCharge.setDetail( detail );
			wxPayCharge.setOutTradeNo( outTradeNo );
			wxPayCharge.setTotalFee( totalFee );
			wxPayCharge.setSpbillCreateIp( spbillCreateIp );
			wxPayCharge.setTradeType( tradeType );
			wxPayCharge.setOpenid( openid );
			wxPayCharge.setResultCode( resultCode );
			wxPayCharge.setErrCode( errCode );
			wxPayCharge.setErrCodeDes( errCodeDes );
			wxPayCharge.setPrepayId( prepayId );
			wxPayCharge.setCodeUrl( codeUrl );
			wxPayCharge.setStatus( WxPayChargeStatus.unpaid.getId() );
			wxPayCharge.setInsertTime( new Date() );
			
			String timeStamp = result.get("timeStamp");
			String nonceStr = result.get("nonceStr");
			String packageStr = result.get("package");
			String signType = result.get("signType");
			String paySign = result.get("paySign");
			
			wxPayCharge.setTimeStamp( timeStamp );
			wxPayCharge.setNonceStr( nonceStr );
			wxPayCharge.setPackageStr( packageStr );
			wxPayCharge.setSignType( signType );
			wxPayCharge.setPaySign( paySign );
			
			this.save( wxPayCharge );
			
			return wxPayCharge;
			
		} catch(Exception e) {
			
			return null;
		}
		
	}
	
	
	@Override
	public void dealPaySuccess(WxPayCharge wxPayCharge, WxPayNotice wxPayNotice) {
		
		wxPayCharge.setWxPayNotice( wxPayNotice );
		wxPayCharge.setStatus( WxPayChargeStatus.paySuccess.getId() );
		this.update( wxPayCharge );
		
		finChargeService.dealPaySuccess( wxPayCharge.getFinCharge() );
		
	}
	
	
	@Override
	public void dealPayFail(WxPayCharge wxPayCharge, WxPayNotice wxPayNotice) {
		
		wxPayCharge.setWxPayNotice( wxPayNotice );
		wxPayCharge.setStatus( WxPayChargeStatus.payFail.getId() );
		this.update( wxPayCharge );
		
		finChargeService.dealPayFail( wxPayCharge.getFinCharge() );
		
	}
	
	
	@Override
	public void dealPayFinish(WxPayCharge wxPayCharge) {
		
		if ( wxPayCharge.getStatus() != WxPayChargeStatus.unpaid.getId() ) 
			return;
		
		wxPayChargeDao.dealPayFinish( wxPayCharge );
		
		finChargeService.dealPayFinish( wxPayCharge.getFinCharge() );
		
	}
	
	
}
