package com.djy.point.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.djy.wechat.service.CallbackService;
import com.frame.base.web.controller.WebController;
import com.frame.wechat.config.WechatParameter;
import com.frame.wechat.util.SHA1;

@Controller
@RequestMapping("/wxPub")
public class WxPubController extends WebController {

	private static Logger logger = LoggerFactory.getLogger(WxPubController.class);
	
	@Autowired
	private CallbackService callbackService;
	
	
	@RequestMapping(value="/callback.do", method=RequestMethod.GET)
	public void doGetCallback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String signature = req.getParameter("signature");
			String echostr = req.getParameter("echostr");

			if (signature.equals(SHA1.gen(WechatParameter.token, timestamp, nonce))) {
				out(echostr, resp);
			} else {
				out("", resp);
			}
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			logger.error(e.getMessage());
			
			out("", resp);
		}
	}
	
	
	@RequestMapping(value="/callback.do", method=RequestMethod.POST)
	public void doPostCallback(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		
		try {
			
			req.setCharacterEncoding("UTF-8");
			
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String signature = req.getParameter("signature");
			if (!signature.equals(SHA1.gen(WechatParameter.token, timestamp, nonce))) {
				out("", resp);
				return;
			}
			
			Map<String, String> reqMap = parseXml(req.getInputStream());
			
			String xmlStr = callbackService.handle( reqMap );
			
			xmlStr = (xmlStr == null) ? "" : xmlStr;

			out(xmlStr, resp);

		} catch (Throwable e) {
			
			//e.printStackTrace();
			logger.error(e.getMessage(), e);
			
			out("", resp);
		}
	}
	
	
	
	protected void out(String str, HttpServletResponse response) {
		Writer out = null;
		try {
			response.setContentType("text/xml;charset=UTF-8");
			out = response.getWriter();
			out.append(str);
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private static Map<String, String> parseXml(InputStream in) throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

}
