package com.phshopping.api.controller.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  MemberPasswordDTO   
 * @Description:会员密码传输dto   
 * @author: lijie
 * @date:   2017年5月18日 下午5:36:30     
 * @Copyright: 2017
 */
public class AppMemberPasswordDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = 7005304431416847717L;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 手机号
	 */
	private String telPhone;
	/**
	 *新密码
	 */
	private String newPassword;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 请求标识
	 */
	private String token;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
