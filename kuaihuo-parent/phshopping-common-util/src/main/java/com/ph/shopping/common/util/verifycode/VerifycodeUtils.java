package com.ph.shopping.common.util.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bingoohuang.patchca.background.SingleColorBackgroundFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

public class VerifycodeUtils {

	private static final Logger logger = LoggerFactory.getLogger(VerifycodeUtils.class);

	/**
	 * 使用最新的验证码生成器
	 * 
	 * @param request
	 * @param response
	 */
	public static void makeVerifyImage(HttpServletRequest request, HttpServletResponse response, int size) {

		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		try {
			ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
			cs.setBackgroundFactory(new SingleColorBackgroundFactory(VerifycodeUtils.getRandColor(200, 230)));
			cs.setColorFactory(new SingleColorFactory(VerifycodeUtils.getRandColor(110, 180)));
			cs.setFilterFactory(
					new CurvesRippleFilterFactory(new SingleColorFactory(VerifycodeUtils.getRandColor(150, 200))));
			RandomWordFactory wordFactory = new RandomWordFactory();
			wordFactory.setMaxLength(size);
			wordFactory.setMinLength(size);
			cs.setWordFactory(wordFactory);
			// 强制获取session
			HttpSession session = request.getSession(true);
			OutputStream out = response.getOutputStream();
			String verifycode = EncoderHelper.getChallangeAndWriteImage(cs, "png", out);
			// 将认证码存入SESSION
			session.setAttribute("verifycode", verifycode);
			out.flush();
			out.close();
		} catch (IOException ex) {
			logger.error("生成验证码图片出错", ex);
		}
	}
	
	public static void makeVerifyImageByNum(HttpServletRequest request, HttpServletResponse response, int codeCount) {

		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuilder sbud = new StringBuilder();
		for (int i = 0; i < codeCount; i++) {
			String rand = String.valueOf(codeSequence[random.nextInt(36)]);
			sbud.append(rand);
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 16);
		}
		// 强制获取session
		HttpSession session = request.getSession(true);
		// 将认证码存入SESSION
		session.setAttribute("verifycode", sbud.toString());
		// 图象生效
		g.dispose();
		// 输出图象到页面
		try {
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
			if (null != out) {
				out.flush();
				out.close();
			}
		} catch (IOException ex) {
			logger.error("生成验证码图片出错", ex);
		}
	}

	public static String getVerifyCodeBySession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "";
		}
		if (session.getAttribute("verifycode") == null) {
			return "";
		}
		return session.getAttribute("verifycode").toString();
	}

	protected static Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
