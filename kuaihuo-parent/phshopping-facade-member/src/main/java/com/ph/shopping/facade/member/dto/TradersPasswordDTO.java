package com.ph.shopping.facade.member.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: TradersPasswordDTO
 * @Description:交易密码DTO
 * @author 王强
 * @date 2017年6月19日 上午10:38:12
 */
public class TradersPasswordDTO implements Serializable {

	private static final long serialVersionUID = -5432768492574626123L;

	/**
	 * 密码类型
	 */
	private Byte customerType;

	/**
	 * 确认密码
	 */
	private String confirmPwd;
	/**
	 * 密码
	 */
	private String newPwd;
	/**
	 * 密码使用者ID
	 */
	private Long userId;

	/**
	 * 登录手机号
	 */
	private String telPhone;

	public Byte getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Byte customerType) {
		this.customerType = customerType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
}
