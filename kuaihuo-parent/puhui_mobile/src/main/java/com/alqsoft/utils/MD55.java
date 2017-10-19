package com.alqsoft.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @椤圭洰锛歱hshopping-common-util
 *
 * @鎻忚堪锛歁D5鍔犲瘑宸ュ叿绫�
 *
 * @浣滆�咃細 Mr.chang
 *
 * @鍒涘缓鏃堕棿锛�2017骞�3鏈�8鏃�
 *
 * @Copyright @2017 by Mr.chang
 */
public class MD55 {
	
	private static Logger log = LoggerFactory.getLogger(MD55.class);

	public static byte[] getMD5(byte[] data) {
		byte[] md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md5 = md.digest(data);
		} catch (Exception e) {
		}
		return md5;
	}

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			if (!StringUtils.isEmpty(str)) {
				messageDigest.update(str.getBytes("UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	/*
	 * MD5瀛楃涓�
	 */
	public static String getMD5Str(byte[] data) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(data);
		} catch (NoSuchAlgorithmException e) {
			// LogUtil.e("NoSuchAlgorithmException = " + e.toString());
			log.error(e.getMessage());
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	public static void main(String[] args){
		System.out.println(MD55.getMD5Str("123456"));
//		System.out.println(DigestUtils.md5DigestAsHex("chenjianhua".getBytes()).toUpperCase());
		
	}
}
