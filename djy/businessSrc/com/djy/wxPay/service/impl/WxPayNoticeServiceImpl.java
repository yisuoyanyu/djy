package com.djy.wxPay.service.impl;


import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.wxPay.enumtype.WxPayChargeStatus;
import com.djy.wxPay.model.WxPayCharge;
import com.djy.wxPay.model.WxPayNotice;
import com.djy.wxPay.service.WxPayChargeService;
import com.djy.wxPay.service.WxPayNoticeService;
import com.frame.base.service.impl.BaseServiceImpl;


@Service("wxPayNoticeService")
public class WxPayNoticeServiceImpl extends BaseServiceImpl<WxPayNotice> implements WxPayNoticeService {
	
	@Autowired
	private WxPayChargeService wxPayChargeService;
	
	
	@Override
	public WxPayNotice creatByResult(String resultXml, Map<String, String> resultMap) {
		
		WxPayNotice wxPayNotice = new WxPayNotice();
		
		// 公众账号ID
		String appid = (String)resultMap.get("appid");
		wxPayNotice.setAppid( appid );
		
		// 商户号
		String mchId = (String)resultMap.get("mch_id");
		wxPayNotice.setMchId( mchId );
		
		// 设备号
		String deviceInfo = (String)resultMap.get("device_info");
		wxPayNotice.setDeviceInfo( deviceInfo );
		
		// 随机字符串
		String nonceStr = (String)resultMap.get("nonce_str");
		wxPayNotice.setNonceStr( nonceStr );
		
		// 业务结果：SUCCESS/FAIL
		String resultCode = (String)resultMap.get("result_code");
		wxPayNotice.setResultCode( resultCode );
		
		// 错误代码
		String errCode = (String)resultMap.get("err_code");
		wxPayNotice.setErrCode( errCode );
		
		// 错误代码描述
		String errCodeDes = (String)resultMap.get("err_code_des");
		wxPayNotice.setErrCodeDes( errCodeDes );
		
		// 用户标识
		String openid = (String)resultMap.get("openid");
		wxPayNotice.setOpenid( openid );
		
		// 用户是否关注公众账号，Y-关注，N-未关注。
		String isSubscribe = null;
		if(resultMap.get("is_subscribe") != null)
			isSubscribe = (String)resultMap.get("is_subscribe");
		wxPayNotice.setIsSubscribe( isSubscribe );
		
		// 交易类型：JSAPI、NATIVE、APP
		String tradeType = null;
		if(resultMap.get("trade_type") != null)
			tradeType = (String)resultMap.get("trade_type");
		wxPayNotice.setTradeType( tradeType );
		
		// 付款银行
		String bankType = null;
		if(resultMap.get("bank_type") != null)
			bankType = (String)resultMap.get("bank_type");
		wxPayNotice.setBankType( bankType );
		
		// 订单总金额，单位为分。
		Integer totalFee = null;
		if(resultMap.get("total_fee") != null)
			totalFee = new Integer( resultMap.get("total_fee") );
		wxPayNotice.setTotalFee( totalFee );
		
		// 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
		Integer settlementTotalFee = null;
		if ( resultMap.get("settlement_total_fee") != null )
			settlementTotalFee = new Integer( resultMap.get("settlement_total_fee") );
		wxPayNotice.setSettlementTotalFee( settlementTotalFee );
		
		// 货币种类
		String feeType = null;
		if(resultMap.get("fee_type") != null)
			feeType = (String)resultMap.get("fee_type");
		wxPayNotice.setFeeType( feeType );
		
		// 现金支付金额
		Integer cashFee = null;
		if(resultMap.get("cash_fee") != null)
			cashFee = new Integer( resultMap.get("cash_fee") );
		wxPayNotice.setCashFee( cashFee );
		
		// 现金支付货币类型
		String cashFeeType = null;
		if(resultMap.get("cash_fee_type") != null)
			cashFeeType = (String)resultMap.get("cash_fee_type");
		wxPayNotice.setCashFeeType( cashFeeType );
		
		// 总代金券金额
		Integer couponFee = null;
		if ( resultMap.get("coupon_fee") != null )
			settlementTotalFee = new Integer( resultMap.get("coupon_fee") );
		wxPayNotice.setCouponFee( couponFee );
		
		// 代金券使用数量
		Integer couponCount = null;
		if ( resultMap.get("coupon_count") != null )
			settlementTotalFee = new Integer( resultMap.get("coupon_count") );
		wxPayNotice.setCouponCount( couponCount );
		
		// 微信支付订单号
		String transactionId = null;
		if(resultMap.get("transaction_id") != null)
			transactionId = (String)resultMap.get("transaction_id");
		wxPayNotice.setTransactionId( transactionId );
		
		// 商户订单号
		String outTradeNo = null;
		if(resultMap.get("out_trade_no") != null)
			outTradeNo = (String)resultMap.get("out_trade_no");
		wxPayNotice.setOutTradeNo( outTradeNo );
		
		// 商家数据包
		String attach = null;
		if ( resultMap.get("attach") != null )
			attach = (String)resultMap.get("attach");
		wxPayNotice.setAttach( attach );
		
		// 支付完成时间
		String timeEnd = null;
		if (resultMap.get("time_end") != null) 
		     timeEnd = (String)resultMap.get("time_end");
		wxPayNotice.setTimeEnd( timeEnd );
		
		wxPayNotice.setResultXml( resultXml );
		wxPayNotice.setInsertTime( new Date() );
		
		this.save( wxPayNotice );
		
		return wxPayNotice;
		
	}
	
	
	
	@Override
	public void dealWxPayNotice(WxPayNotice wxPayNotice) {
		
		String outTradeNo = wxPayNotice.getOutTradeNo();
		
		WxPayCharge wxPayCharge = wxPayChargeService.getByOutTradeNo( outTradeNo );
		
		if ( wxPayCharge == null )
			return;
		
		if ( wxPayCharge.getStatus() != WxPayChargeStatus.unpaid.getId() 
				&& wxPayCharge.getStatus() != WxPayChargeStatus.paying.getId() )
			return;
		
		if ( "SUCCESS".equals( wxPayNotice.getResultCode() ) ) {	// 支付成功
			
			wxPayChargeService.dealPaySuccess(wxPayCharge, wxPayNotice);
			
		} else if ( "FAIL".equals( wxPayNotice.getResultCode() ) ) {	// 支付失败
			
			wxPayChargeService.dealPayFail(wxPayCharge, wxPayNotice);
			
		}
		
	}
	
}
