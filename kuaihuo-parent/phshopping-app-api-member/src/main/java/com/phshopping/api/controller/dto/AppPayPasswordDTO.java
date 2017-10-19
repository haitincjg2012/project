package com.phshopping.api.controller.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  PayPasswordDTO   
 * @Description:支付密码传输dto   
 * @author: lijie
 * @date:   2017年5月18日 下午5:38:10     
 * @Copyright: 2017
 */
public class AppPayPasswordDTO implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -6234162636928699094L;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * token
	 */
	private String token;
	/**
	 * 手机号
	 */
	private String telPhone;
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
}
