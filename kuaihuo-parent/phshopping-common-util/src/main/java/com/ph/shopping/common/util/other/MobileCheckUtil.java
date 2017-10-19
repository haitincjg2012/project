package com.ph.shopping.common.util.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @ClassName:  PhoneFormatCheckUtils   
 * @Description:手机号格式校验 
 * @author: lijie
 * @date:   2017年4月18日 上午11:35:48     
 * @Copyright: 2017
 */
public class MobileCheckUtil {
	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str) {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 
	 * @Title: isChinaPhoneLegal   
	 * @Description: 手机号匹配   
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isChinaPhoneLegal(String str) {
		String regExp = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
