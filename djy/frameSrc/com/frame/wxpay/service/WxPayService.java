package com.frame.wxpay.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.frame.wxpay.config.WXPayParameter;
import com.frame.wxpay.sdk.WXPay;
import com.frame.wxpay.sdk.WXPayUtil;
import com.frame.wxpay.service.impl.WXPayConfigImpl;

@Service("wxPayService")
public class WxPayService {
	
	private WXPay wxpay;
	private WXPayConfigImpl config;
	
	
	public WxPayService() throws Exception {
		config = WXPayConfigImpl.getInstance();
		wxpay = new WXPay( config, true, WXPayParameter.useSandbox );
	}
	
	//------------------------------------------------------------------
	
	/**
	 * 发起支付，统一下单
	 * @param data 传递的键值对参数
	 *     {
	 *         "device_info" : （可选）设备号, 
	 *         "body" : （必填）商品描述, 
	 *         "detail" : （可选）商品详情, 
	 *         "out_trade_no" : （必填）商户系统内部订单号, 
	 *         "fee_type" : （可选）标价币种（默认人民币：CNY）, 
	 *         "total_fee" : （必填）订单总金额，单位为分。
	 *         "spbill_create_ip" : （必填）终端IP, 
	 *         "trade_type" : （必填）交易类型，取值为：JSAPI，NATIVE，APP等,
	 *         "openid" : 用户标识。交易类型为JSAPI（即公众号支付）时，此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 *     }
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unifiedOrder(Map<String, String> data) throws Exception {
		Map<String, String> reqData = new HashMap<>();
		for ( String key : data.keySet() ) {
			reqData.put(key, data.get(key));
		}
		reqData.put("notify_url", WXPayParameter.notifyServerUrl + "notice/wxpay/notifyUrl.do");
		Map<String, String>  respData = wxpay.unifiedOrder( reqData );
		String resultCode = respData.get("result_code");
		if ( "SUCCESS".equals( resultCode ) ) {
			String appid = respData.get("appid");
			String prepayId = respData.get("prepay_id");
			
			// 时间戳
			String timeStamp = new Long(WXPayUtil.getCurrentTimestamp()).toString();
			respData.put("timeStamp", timeStamp);
						
			// 随机字符串
			String nonceStr = WXPayUtil.generateUUID();
			respData.put("nonceStr", nonceStr);
			
			// 订单详情扩展字符串
			String packageStr = "prepay_id=" + prepayId;
			respData.put("package", packageStr);
			
			// 签名方式
			String signType = wxpay.getSignTypeStr();
			respData.put("signType", signType);
			
			// 计算支付签名
			Map<String, String> payData = new HashMap<>();
			payData.put("appId", appid);
			payData.put("timeStamp", timeStamp);
			payData.put("nonceStr", nonceStr);
			payData.put("package", packageStr);
			payData.put("signType", signType);
			String paySign = WXPayUtil.generateSignature( payData , WXPayParameter.key , wxpay.getSignType() );
			respData.put("paySign", paySign);
		}
		
		
		return respData;
	}
	
	
	//------------------------------------------------------------------
	
	
	/**
	 * 发起企业付款
	 * @param data 传递的键值对参数
	 *     {
	 *         "device_info" : （可选）设备号, 
	 *         "partner_trade_no" : （必填）商户订单号, 
	 *         "openid" : （必填）用户openid。商户appid下，某用户的openid。, 
	 *         "check_name" : （必填）校验用户姓名选项。NO_CHECK：不校验真实姓名，FORCE_CHECK：强校验真实姓名。, 
	 *         "re_user_name" : 收款用户姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名。, 
	 *         "amount" : （必填）企业付款金额，单位为分。, 
	 *         "desc" : （必填）企业付款操作说明信息。必填。, 
	 *         "spbill_create_ip" : （必填）调用接口的机器Ip地址, 
	 *     }
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> transfers(Map<String, String> data) throws Exception {
		Map<String, String> reqData = new HashMap<>();
		for ( String key : data.keySet() ) {
			reqData.put(key, data.get(key));
		}
		return wxpay.transfers( reqData );
	}
	
	
	//------------------------------------------------------------------
	
	/**
	 * 获取request请求的字符串信息
	 * @param request
	 * @return
	 */
	public String getReqStr( HttpServletRequest request ) {
		java.io.BufferedReader bis = null;
		String result = "";
		try{
			bis = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = bis.readLine()) != null){
				result += line;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bis != null){
				try{
					bis.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	/**
	 * xml字符串 转为 map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getMapByXml(String strXml) throws Exception {
		Map<String, String> data = WXPayUtil.xmlToMap( strXml );
		return data;
	}
	
	/**
	 * 判断支付结果通知中sign是否有效
	 * @param requestStr
	 * @return
	 * @throws Exception
	 */
	public boolean isNotifySignatureValid(Map<String, String> reqData) throws Exception {
		return wxpay.isPayResultNotifySignatureValid( reqData );
	}
	
	
	//------------------------------------------------------------------
	
	public String getSandboxSignKey() throws Exception {
		Map<String, String> respMap = wxpay.getSandboxSignKey();
		return respMap.get("sandbox_signkey");
	}
	
}
