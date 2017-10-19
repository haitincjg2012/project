package com.phshopping.api.controller.dto;

import java.io.Serializable;
/**
 * 
 * @ClassName:  CertificatesAuthDTO   
 * @Description:认证传输dto 
 * @author: lijie
 * @date:   2017年5月18日 下午5:39:05     
 * @Copyright: 2017
 */
public class AppCertificateAuthDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -4436818327519493525L;
	/**
	 * 证件名称
	 */
	private String name;
	/**
	 * 证件号码
	 */
	private String idCode;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 请求标识
	 */
	private String token;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
