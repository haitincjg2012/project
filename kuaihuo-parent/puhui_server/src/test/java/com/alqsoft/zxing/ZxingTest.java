/**
 * Project Name:alqsoft-easyui
 * File Name:ZxingTest.java
 * Package Name:com.alqsoft.zxing
 * Date:2016年3月14日下午5:10:16
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.zxing;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.alqframework.utils.UniqueUtils;
import org.alqframework.zxing.ZxingUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.util.HtmlUtils;

import com.alqsoft.utils.UtilDate;

/**
 * ClassName:ZxingTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月14日 下午5:10:16 <br/>
 * @author   张灿
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ZxingTest {
	
	/**
	 * 生成二维码
	 */
	@Test
	public void test() {
		ZxingUtils.createQrCode("d://", "a", "png", "12456",200,200);
	}
	/**
	 * 生成条形码
	 */
	@Test
	public void test1(){
		boolean result = ZxingUtils.createBarCode("690"+RandomStringUtils.randomNumeric(10), 100, 80, "d://", RandomStringUtils.randomAlphabetic(5), "png");
	}
	
	@Test
	public void password(){
		StandardPasswordEncoder standardPasswordEncoder=new StandardPasswordEncoder("alqsoft");
		System.out.println(standardPasswordEncoder.encode("123456"));
	}

	@Test
	public void guabsh(){
		
		String uu = "&#xff0c;、&#xff1a;我的&#xff0c;&#64;&#xff01;";
			System.out.println(HtmlUtils.htmlUnescape(uu));
	}
	
}
