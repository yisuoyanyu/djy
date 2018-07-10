package com.djy.wxPay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.fin.model.FinTransfer;
import com.djy.wxPay.enumtype.WxPayTransferStatus;
import com.djy.wxPay.model.WxPayTransfer;
import com.djy.wxPay.service.WxPayTransferService;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.wxpay.service.WxPayService;


@Service("wxPayTransferService")
public class WxPayTransferServiceImpl extends BaseServiceImpl<WxPayTransfer> implements WxPayTransferService {
	
	@Autowired
	private WxPayService wxPayService;
	
	
	@Override
	public WxPayTransfer createByFinTransfer(FinTransfer finTransfer, String ip) {
		
		try {
			
			String partnerTradeNo = finTransfer.getOrderNo();
			String openid = finTransfer.getUser().getWechatUser().getOpenid();
			String checkName = "NO_CHECK";
			Integer amount = new Double(finTransfer.getAmount() * 100).intValue();
			String desc = finTransfer.getDescription();
			String spbillCreateIp = ip;
			
			// 调用支付API
			Map<String, String> data = new HashMap<>();
			data.put("partner_trade_no", partnerTradeNo);
			data.put("openid", openid);
			data.put("check_name", checkName);
			
			data.put("amount", amount.toString());
			// data.put("amount", "100");		// 测试环境暂时支付1元
			
			data.put("desc", desc);
			data.put("spbill_create_ip", spbillCreateIp);
			
			Map<String, String> result = wxPayService.transfers( data );
			
			String mchAppid = result.get("mch_appid");
			String mchid = result.get("mchid");
			String deviceInfo = result.get("device_info");
			String nonceStr = result.get("nonce_str");
			String resultCode = result.get("result_code");
			String errCode = result.get("err_code");
			String errCodeDes = result.get("err_code_des");
			String paymentNo = result.get("paymentNo");
			String paymentTime = result.get("paymentTime");
			
			WxPayTransfer wxPayTransfer = new WxPayTransfer();
			wxPayTransfer.setFinTransfer( finTransfer );
			wxPayTransfer.setMchAppid( mchAppid );
			wxPayTransfer.setMchid( mchid );
			wxPayTransfer.setDeviceInfo( deviceInfo );
			wxPayTransfer.setNonceStr( nonceStr );
			wxPayTransfer.setPartnerTradeNo( partnerTradeNo );
			wxPayTransfer.setOpenid( openid );
			wxPayTransfer.setCheckName( checkName );
			wxPayTransfer.setAmount( amount );
			wxPayTransfer.setDescription( desc );
			wxPayTransfer.setSpbillCreateIp( spbillCreateIp );
			wxPayTransfer.setResultCode( resultCode );
			wxPayTransfer.setErrCode( errCode );
			wxPayTransfer.setErrCodeDes( errCodeDes );
			wxPayTransfer.setPaymentNo( paymentNo );
			wxPayTransfer.setPaymentTime( paymentTime );
			if ( "SUCCESS".equals( wxPayTransfer.getResultCode() ) ) {
				wxPayTransfer.setStatus( WxPayTransferStatus.paySuccess.getId() );
			} else {
				wxPayTransfer.setStatus( WxPayTransferStatus.payFail.getId() );
			}
			wxPayTransfer.setInsertTime( new Date() );
			
			this.save( wxPayTransfer );
			
			return wxPayTransfer;
			
		} catch(Exception e) {
			
			return null;
		}
		
	}
	
}
