package com.djy.point.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.qrcode.QRCodeUtil;
import com.djy.point.vo.WxConfigVo;
import com.djy.utils.Base64Encryption;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;
import com.frame.wechat.api.utils.SignUtil;


@Controller
@RequestMapping("/point/main")
public class ZbhMianController extends WebController {
	private static Logger logger = LoggerFactory.getLogger(ZbhMianController.class);

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/testIndex.do")
	public String testIndex(HttpSession session, Model m) {
		/*
		 * 生成带参数的临时二维码和永久二维码
		 */
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("expire_seconds", 604800);//该二维码有效时间，以秒为单位。
		// 最大不超过604800（即7天）。
		// jsonObject.put("action_name",
		// "QR_SCENE");//二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
		// JSONObject jsonObject2 = new JSONObject();
		// jsonObject2.put("scene_id",
		// 7575);//场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
		// JSONObject jsonObject3 =new JSONObject();
		// jsonObject3.put("scene", jsonObject2);
		// jsonObject.put("action_info", jsonObject3);//二维码详细信息
		// System.out.println(jsonObject);
		//
		// QrcodeTicket qrcodeTicket = MpApi.getQrTicket(jsonObject);
		// String url = MpApi.getSceneUrl(qrcodeTicket.getTicket());
		// System.out.println("临时二维码"+url);//通过这个网址就可以获取到二维码信息
		//
		// JSONObject jsonObject4 = new JSONObject();
		// jsonObject4.put("action_name",
		// "QR_LIMIT_STR_SCENE");//二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
		// JSONObject jsonObject5 = new JSONObject();
		// jsonObject5.put("scene_str",
		// "丁大帅");//场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
		// JSONObject jsonObject6 =new JSONObject();
		// jsonObject6.put("scene", jsonObject5);
		// jsonObject4.put("action_info", jsonObject6);//二维码详细信息
		// System.out.println(jsonObject4);
		//
		// QrcodeTicket qrcodeTicket1 = MpApi.getQrTicket(jsonObject4);
		// String url1 = MpApi.getSceneUrl(qrcodeTicket1.getTicket());
		// System.out.println("永久二维码"+url1);//通过这个网址就可以获取到二维码信息【不用在我们本地保存，到时候直接给用户用img
		// src的地址表示即可】
		//
		// System.out.println("fu");
		WxConfigVo wxConfig = WxConfigVo.transfer(SignUtil.getSign(request));
		m.addAttribute("wxConfig", wxConfig);

		return "/point/test/index";
	}

	/* 生成商家让顾客支付积分的二维码图片；这个是在后台的 */
	@RequestMapping(value = "/getMoneyQrcode.do")
	@ResponseBody
	public Result getMoneyQrcode(HttpSession session, HttpServletRequest request) throws IOException {
		String point = "2500";
		String textt = "1zbh" + point; // 到时候这个地方可以采取加密的方式，传入后台以后再进行解密
		String finalExhcange = Base64Encryption.encrypt(textt) + "point:" + point;
		BufferedImage img = QRCodeUtil.getRootImg(finalExhcange, true);
		byte[] data = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		data = new byte[is.available()];
		is.read(data);
		is.close();

		// 返回Base64编码过的字节数组字符串
		return new Result(true, "data:image/jpeg;base64," + Base64.encodeBase64String(data));
	}

	public static void main(String[] args) {
		// String eventKey = "qrscene_coId_75";
		// String sceneStr = eventKey.substring(8);
		// Integer coId = Integer.valueOf(sceneStr.substring(5));
		// System.out.println(coId);
		// double[] result = new double[2];
		// try {
		// String city = "北京";
		// String addr = "北京市朝阳区叶青大厦";
		// String key = "g1YFp2XkPvVxMFSFFy2KsFcuYS2TvEmX";
		//
		// if (!StringUtils.isEmpty(city) && !StringUtils.isEmpty(addr) &&
		// !StringUtils.isEmpty(key)) {
		// String response = getSoundCode(BAIDU_GEOCODER + "?city=" +
		// URLEncoder.encode(city, CharEncoding.UTF_8) + "&address=" +
		// URLEncoder.encode(addr, CharEncoding.UTF_8) + "&output=json&ak=" +
		// key, null, null, null, null, false);
		// if (!StringUtils.isEmpty(response)) {
		// Map<String, Object> map = GsonUtils.fromJson(response, Map.class);
		// if (((Double) map.get("status")).intValue() == 0) {
		// Map<String, Object> location = (Map<String, Object>) ((Map<String,
		// Object>) map.get("result")).get("location");
		// result = new double[] { (double) location.get("lng"), (double)
		// location.get("lat") };
		// }
		// }
		// }
		// } catch (Exception e) {
		// log.error("根据城市和详细地址获得地理坐标", e);
		// }
		// return result;

	}
}
