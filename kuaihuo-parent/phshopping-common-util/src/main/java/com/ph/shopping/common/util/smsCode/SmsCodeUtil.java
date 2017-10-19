package com.ph.shopping.common.util.smsCode;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * phshopping-common-util
 *
 * @description：生产随机密码工具
 *
 * @author：liuy
 *
 * @createTime：2017年4月26日
 *
 * @Copyright @2017 by liuy
 */
public class SmsCodeUtil {

    
	/**
	 * 生成随机密码、验证码 6位数字
     * @return 随6位随机数
     * @author liuy
     * @createTime 2017年04月26日
	 */
	public static String getSmsCode() {
		return RandomStringUtils.random(6, "0123456789");
	}
	 
	/**
	 * 生成随机密码,6位，字母开头+5位数字
     * @return 随机密码
     * @author liuy
     * @createTime 2017年04月26日
	 */
	public static String getMemberPwdCode() {
		char c = (char) (Math.random() * 26 + 97);
		return c + RandomStringUtils.random(5, "0123456789");
	}
}
