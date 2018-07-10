package com.djy.point.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.djy.point.vo.WxConfigVo;
import com.djy.user.model.User;
import com.djy.user.service.UserService;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.controller.WebController;
import com.frame.qrcode.QRCodeUtil;
import com.frame.wechat.api.MpApi;
import com.frame.wechat.api.json.QrcodeTicket;
import com.frame.wechat.api.utils.SignUtil;



@Controller
@RequestMapping("/point/qrCode")
public class QrCodeController extends WebController {
	
	private static Logger logger = LoggerFactory.getLogger( QrCodeController.class );
	
	private static final String ShowQrcode = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	@Autowired
	private UserService userService;
		
	
	@RequestMapping("/myQrcod.do")
	public String myQrcod(
			@RequestParam(value = "spreadCode", required = false) String spreadCode,
			HttpServletRequest request, HttpSession session, Model m) throws IOException {
		
		User user;
		Integer userId = (Integer) session.getAttribute("userId");
		if (StringUtil.isEmpty(spreadCode)) {
			user = userService.getById(userId);
		} else {
			user = userService.getUserBySpreadCode( spreadCode );
		}

		// 第一次查看自己 的二维码的时候要给它创建
		if ( StringUtil.isBlank( user.getQrcodeAddress() ) 
				|| StringUtil.isBlank( user.getQrcodeUrlStr() ) ) {
			
			QrcodeTicket qrcodeTicket = MpApi.getQrLimitStrSceneTicket( user.getSpreadCode() );
			String QrcodeAddress = MpApi.getSceneUrl( qrcodeTicket.getTicket() );
			String QrcodeUrlStr = qrcodeTicket.getUrl();
			
			user.setQrcodeAddress( QrcodeAddress );
			user.setQrcodeUrlStr( QrcodeUrlStr );
			
			userService.update( user );
		}
		
		// 到时候这个地方可以采取加密的方式，传入后台以后再进行解密
//		String textt = "zbhcopId;" + user.getCoPartner().getId();
//		
//		BufferedImage img = QRCodeUtil.getRootImg(textt, true);
//		byte[] data = null;
//		ByteArrayOutputStream os = new ByteArrayOutputStream();  
//		ImageIO.write(img, "jpg", os);  
//		InputStream is = new ByteArrayInputStream(os.toByteArray()); 
//		data = new byte[is.available()];  
//		is.read(data);  
//		is.close(); 
//		
//		// 返回Base64编码过的字节数组字符串  
//		String qrImg = "data:image/jpeg;base64," + Base64.encodeBase64String( data );
		
		WxConfigVo wxConfig = WxConfigVo.transfer( SignUtil.getSign(request) );
		m.addAttribute("wxConfig", wxConfig);
		//m.addAttribute("qrImg", qrImg);
		m.addAttribute("user", user);
		return "/point/qrCode/myQrcod";
	}
	
	@RequestMapping("/showDjyQrcod.do")
	public String showDjyQrcod( HttpSession session, Model m){
		
		return "/point/qrCode/showDjyQrcod";
	}
	
	public static void main(String []args) {
//		String url = MpApi.getQrLimitStrSceneUrl( "COPARTNER" );
//		System.out.println(url);
	}
	
}
