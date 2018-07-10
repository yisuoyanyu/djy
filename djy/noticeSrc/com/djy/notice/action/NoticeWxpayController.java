package com.djy.notice.action;


import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.wxPay.model.WxPayNotice;
import com.djy.wxPay.service.WxPayNoticeService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.wxpay.service.WxPayService;


@Controller
@RequestMapping("/notice/wxpay")
public class NoticeWxpayController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger( NoticeWxpayController.class );
	
	
	@Autowired
	private WxPayService wxPaySerivce;
	
	@Autowired
	private WxPayNoticeService wxPayNoticeService;
	
	
	
	@RequestMapping("/notifyUrl.do")
	@ResponseBody
	public void notifyUrl(
			HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model m ) {
		
		try {
			String reqStr = wxPaySerivce.getReqStr( request );
			
			if ( StringUtil.isBlank( reqStr ) )
				return;
			
			Map<String, String> reqData = wxPaySerivce.getMapByXml( reqStr );
			
			if ( reqData == null || reqData.size() == 0 ) {
				return;
			}
			
			if ( wxPaySerivce.isNotifySignatureValid( reqData ) ) {	// 签名校验成功
				
				// 返回状态码
				String returnCode = (String)reqData.get("return_code");
				
				// 返回信息，如非空，为错误原因：
				// 签名失败 
				// 参数格式校验错误
				// ……
				String returnMsg = (String)reqData.get("return_msg");
				
				if ( "SUCCESS".equals( returnCode ) ) {
					
					// 保存 到 wx_pay_notice
					WxPayNotice wxPayNotice = wxPayNoticeService.creatByResult(reqStr, reqData);
					
					wxPayNoticeService.dealWxPayNotice( wxPayNotice );
					
				} else {
					// returnMsg 如非空，为错误原因：
					// 签名失败、参数格式校验错误……
					logger.error( returnMsg );
				}
				
				
				/*
				// 注意：按这种方式在response.getWriter().println()会报错，同时会收到多条回调消息。
				response.setContentType("text/xml");
				String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" 
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );
				out.write( resXml.getBytes() );
				out.flush();
				out.close();
				
				response.getWriter().println("success");
				*/
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/xml;charset=UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" 
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				response.getWriter().write( resXml );
				
			} else {	// 签名校验失败
				
				/*
				// 注意：按这种方式在response.getWriter().println()会报错，同时会收到多条回调消息。
				response.setContentType("text/xml");
				String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" 
						+ "<return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
				BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );
				out.write( resXml.getBytes() );
				out.flush();
				out.close();
				
				response.getWriter().println("fail");
				*/
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/xml;charset=UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" 
						+ "<return_msg><![CDATA[签名失败]]></return_msg>" + "</xml>";
				response.getWriter().write( resXml );
				
			}
			
		} catch(IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
}
