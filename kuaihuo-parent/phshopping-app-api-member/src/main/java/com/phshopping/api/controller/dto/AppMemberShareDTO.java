/**  
 * @Title:  AppMemberShareDTO.java   
 * @Package com.phshopping.api.controller.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月25日 上午9:18:03   
 * @version V1.0 
 * @Copyright: 2017
 */
package com.phshopping.api.controller.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * @ClassName: AppMemberShareDTO
 * @Description:会员分享传输数据
 * @author: 李杰
 * @date: 2017年7月25日 上午9:18:03
 * @Copyright: 2017
 */
public class AppMemberShareDTO extends BaseValidate {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4506427112795112062L;

	private String token;
	/**
	 * 分享人手机号
	 */
	@NotBlank(message = "分享人手机号不能为空")
	private String telPhone;
	/**
	 * 分享人ID
	 */
	@NotNull(message = "分享人ID不能为空")
	private Long userId;
	/**
	 * 被分享人手机号
	 */
	@NotBlank(message = "被分享人手机号不能为空")
	private String toTelPhone;
	/**
	 * 短信验证码
	 */
	@NotBlank(message = "短信验证码不能为空")
	private String smsCode;

	@NotNull(message = "分享人类型不能为空")
	private Byte type;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToTelPhone() {
		return toTelPhone;
	}

	public void setToTelPhone(String toTelPhone) {
		this.toTelPhone = toTelPhone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
