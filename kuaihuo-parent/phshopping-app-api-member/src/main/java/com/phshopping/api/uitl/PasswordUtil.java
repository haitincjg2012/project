package com.phshopping.api.uitl;

import org.apache.commons.lang3.StringUtils;

public class PasswordUtil {

	/**
	 * 
	 * @Title: verifyPwd   
	 * @Description: 会员密码规则校验   
	 * @param: @param password
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	public static boolean verifyPwd(String password) {
		if (StringUtils.isNotBlank(password)) {
			// 判断密码长度
			if (password.length() < 6 || password.length() > 16) {
				return false;
			}
			// 判断密码是否已字母开头
			/*if (!Character.isLetter(password.charAt(0))) {
				return false;
			}*/
			// 判断密码是否存在非法字符
			if (password.matches("^[^\u4e00-\u9fa5]+$")) {
				return true;
			}
		}
		return false;
	}
}
