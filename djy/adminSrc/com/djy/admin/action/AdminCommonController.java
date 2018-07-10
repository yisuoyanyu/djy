package com.djy.admin.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.admin.wxpay.sdk.WXHttpUtil;
import com.djy.admin.wxpay.sdk.WXPayCommonUtil;
import com.djy.admin.wxpay.sdk.WXPayUtil;
import com.djy.admin.wxpay.sdk.WXXMLUtil;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.user.service.UserService;
import com.frame.base.utils.DateUtil;
import com.frame.base.web.controller.WebController;
import com.frame.wechat.config.WechatParameter;
import com.frame.wxpay.config.WXPayParameter;

@Controller
@RequestMapping("/admin/common/")
public class AdminCommonController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminCommonController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeOrderService consumeOrderService;
	
	
	/**
	 * 跳转到系统用户管理后台首页
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model m) {
		return "/admin/common/index";
	}
	
	/**
	 * 跳转首页后加载订单统计数据
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/home")
	public String home(Model m) {
		
		try {
			
			Date today = new Date();
			Date yesterday = DateUtil.addDay(today, -1);
			
			Date todayMin = DateUtil.getFirstTimeOfDate(today);
			Date todayMax = DateUtil.getLastTimeOfDate(today);
			
			Date yesterdayMin = DateUtil.getFirstTimeOfDate(yesterday);
			Date yesterdayMax = DateUtil.getLastTimeOfDate(yesterday);
			
			Map<String, Object> todayParam = new HashMap<String, Object>();
			todayParam.put("timeStart", todayMin);
			todayParam.put("timeEnd", todayMax);
			
			Map<String, Object> yesterdayParam = new HashMap<String, Object>();
			yesterdayParam.put("timeStart", yesterdayMin);
			yesterdayParam.put("timeEnd", yesterdayMax);
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			//平台会员
			Integer userAddToday = userService.getUserNum(todayParam).intValue();
			Integer userAddYesterday = userService.getUserNum(yesterdayParam).intValue();
			Integer userAddTotal = userService.getUserNumAll();
			
			//会员消费订单
			Integer consumeOrderNumAddToday = consumeOrderService.getAllConsumeOrderNum(todayParam).intValue();
			Integer consumeOrderNumAddYesterday = consumeOrderService.getAllConsumeOrderNum(yesterdayParam).intValue();
			Integer consumeOrderNumAddTotal = consumeOrderService.getAllConsumeOrderNum(param).intValue();
			
			//会员消费金额
			double consumeAmountToday = consumeOrderService.getConsumeOrderAmount(todayParam);
			double consumeAmountYesterday = consumeOrderService.getConsumeOrderAmount(yesterdayParam);
			double consumeAmountTotal = consumeOrderService.getConsumeOrderAmount(param);
			
			//会员支付金额
			double consumePayAmountToday = consumeOrderService.getConsumeOrderPayAmount(todayParam);
			double consumePayAmountYesterday = consumeOrderService.getConsumeOrderPayAmount(yesterdayParam);
			double consumePayAmountTotal = consumeOrderService.getConsumeOrderPayAmount(param);
			
			//被邀会员
			Integer consumeUserAddToday = userService.getFirstConsumeUserNum(todayParam).intValue();
			Integer consumeUserAddYesterday = userService.getFirstConsumeUserNum(yesterdayParam).intValue();
			Integer consumeUserAddTotal = userService.getFirstConsumeUserNum(param).intValue();
			
			//被邀会员消费订单
			Integer consumeOrderAddToday = consumeOrderService.getConsumeOrderNum(todayParam).intValue();
			Integer consumeOrderAddYesterday = consumeOrderService.getConsumeOrderNum(yesterdayParam).intValue();
			Integer consumeOrderAddTotal = consumeOrderService.getConsumeOrderNum(param).intValue();
			
			//被邀会员消费金额
			double invitedConsumeAmountToday = consumeOrderService.getInvitedConsumeOrderAmount(todayParam);
			double invitedConsumeAmountYesterday = consumeOrderService.getInvitedConsumeOrderAmount(yesterdayParam);
			double invitedConsumeAmountTotal = consumeOrderService.getInvitedConsumeOrderAmount(param);
			
			//被邀会员支付金额
			double invitedConsumePayAmountToday = consumeOrderService.getInvitedConsumeOrderPayAmount(todayParam);
			double invitedConsumePayAmountYesterday = consumeOrderService.getInvitedConsumeOrderPayAmount(yesterdayParam);
			double invitedConsumePayAmountTotal = consumeOrderService.getInvitedConsumeOrderPayAmount(param);			
			
			m.addAttribute("userAddToday", userAddToday);
			m.addAttribute("userAddYesterday", userAddYesterday);
			m.addAttribute("userAddTotal", userAddTotal);
			
			m.addAttribute("consumeOrderNumAddToday", consumeOrderNumAddToday);
			m.addAttribute("consumeOrderNumAddYesterday", consumeOrderNumAddYesterday);
			m.addAttribute("consumeOrderNumAddTotal", consumeOrderNumAddTotal);
			
			m.addAttribute("consumeAmountToday", consumeAmountToday);
			m.addAttribute("consumeAmountYesterday", consumeAmountYesterday);
			m.addAttribute("consumeAmountTotal", consumeAmountTotal);
			
			m.addAttribute("consumePayAmountToday", consumePayAmountToday);
			m.addAttribute("consumePayAmountYesterday", consumePayAmountYesterday);
			m.addAttribute("consumePayAmountTotal", consumePayAmountTotal);
			
			m.addAttribute("consumeUserAddToday", consumeUserAddToday);
			m.addAttribute("consumeUserAddYesterday", consumeUserAddYesterday);
			m.addAttribute("consumeUserAddTotal", consumeUserAddTotal);
			
			m.addAttribute("consumeOrderAddToday", consumeOrderAddToday);
			m.addAttribute("consumeOrderAddYesterday", consumeOrderAddYesterday);
			m.addAttribute("consumeOrderAddTotal", consumeOrderAddTotal);
			
			m.addAttribute("invitedConsumeAmountToday", invitedConsumeAmountToday);
			m.addAttribute("invitedConsumeAmountYesterday", invitedConsumeAmountYesterday);
			m.addAttribute("invitedConsumeAmountTotal", invitedConsumeAmountTotal);
			
			m.addAttribute("invitedConsumePayAmountToday", invitedConsumePayAmountToday);
			m.addAttribute("invitedConsumePayAmountYesterday", invitedConsumePayAmountYesterday);
			m.addAttribute("invitedConsumePayAmountTotal", invitedConsumePayAmountTotal);
			
			return "/admin/common/home";
		} catch (Exception e) {
			logger.error("",e);
			return "";
		}
		
	}
	
	@RequestMapping("/wechatHandle")
	@ResponseBody
	public String wechatHandle(HttpServletRequest httpRequest) throws JDOMException, IOException{
		
		String appid = WechatParameter.appID;  // appid  
        //String appsecret = PayConfigUtil.APP_SECRET; // appsecret  
        String mch_id = WXPayParameter.mchID; // 商业号  
        String key = WXPayParameter.key; // key  

        String nonce_str = WXPayUtil.generateUUID();  
          
        String order_price = "1"; // 价格   注意：价格的单位是分  
        String body = "goodssssss";   // 商品名称  
        String out_trade_no = "11338"; // 订单号  
          
        // 获取发起电脑 ip  
        String spbill_create_ip = httpRequest.getRemoteAddr();
        // 回调接口   
        String notify_url = WXPayParameter.notifyServerUrl + "notice/wxpay/notifyUrl.do";
        String trade_type = "NATIVE";  
          
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();  
        packageParams.put("appid", appid);  
        packageParams.put("mch_id", mch_id);  
        packageParams.put("nonce_str", nonce_str);  
        packageParams.put("body", body);  
        packageParams.put("out_trade_no", out_trade_no);  
        packageParams.put("total_fee", order_price);  
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        packageParams.put("notify_url", notify_url);  
        packageParams.put("trade_type", trade_type);  
  
        String sign = WXPayCommonUtil.createSign("utf-8",packageParams, key);
        packageParams.put("sign", sign);  
          
        String requestXML = WXPayCommonUtil.getRequestXml(packageParams);  
        System.out.println(requestXML);  
   
        String resXml = WXHttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);  
  
          
        Map map = WXXMLUtil.doXMLParse(resXml);  
        //String return_code = (String) map.get("return_code");  
        //String prepay_id = (String) map.get("prepay_id");  
        String urlCode = (String) map.get("code_url");  
        
        int widhtHeight = 300;  
        String EC_level = "L";  
        int margin = 0;  
        urlCode = URLEncoder.encode(urlCode, "UTF-8").replace("+", "%20");  
        String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight  
                + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + urlCode; 
        
		return QRfromGoogle;//生成二维码
	}
	
}
