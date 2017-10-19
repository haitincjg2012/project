package com.alqsoft.utils.weixin;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WXRandCharsUtils {
	private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";   
		SecureRandom random = new SecureRandom();   
		StringBuffer sb = new StringBuffer();
		int number = 0;
		for (int i = 0; i < length; i++) {   
			number = random.nextInt(base.length());   
			sb.append(base.charAt(number));   
		}   
		return sb.toString();   
	}   
	
	/*
	 * 订单开始交易的时间
	 */
	public static String timeStart(){
		return df.format(new Date());
	}
	
	/*
	 * 订单开始交易的时间
	 */
	public static String timeExpire(){
		Calendar now=Calendar.getInstance();
		now.add(Calendar.MINUTE,30);
		return df.format(now.getTimeInMillis());
	}



}
