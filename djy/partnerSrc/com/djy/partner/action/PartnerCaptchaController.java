package com.djy.partner.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.base.utils.ConfigUtil;
import com.frame.base.utils.SessionUtil;

@Controller
@RequestMapping("/partner/captcha/")
public class PartnerCaptchaController {
	
	private static Logger logger = LoggerFactory.getLogger(PartnerCaptchaController.class);
	
	private String randomchars = "abcdfghjkmnpqrstuvxyzABCDEFGHJKLMNPQRSTUWXYZ0123456789";
	
	private Color getRandomColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 生成系统登录的验证码
	 */
	@RequestMapping("/randomCode.do")
	public String randomCode(HttpServletRequest request, HttpServletResponse response) {
		
		int width = 75, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandomColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandomColor(160, 200));

		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		StringBuilder sRand = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			String rand = String.valueOf(randomchars.charAt(random.nextInt(randomchars.length())));
			sRand.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		
		SessionUtil.setAttr(ConfigUtil.get("sys.session.captcha"), sRand.toString());
		
		response.reset();
		response.setContentType("image/jpeg");
		response.setHeader("Content-Disposition", "attachment; filename= \"image.jpg\"");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException ex) {
			logger.debug("create yzm error");
		} finally {
			g.dispose();
		}
		return null;
	}
	
}
