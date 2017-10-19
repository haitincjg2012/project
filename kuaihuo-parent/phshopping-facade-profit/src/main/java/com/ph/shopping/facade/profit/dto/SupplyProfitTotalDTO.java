package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * phshopping-facade-profit
 *
 * @description：供应链总表DTO
 *
 * @author：Mr.Dong
 *
 * @createTime：2017年5月16日
 *
 * @Copyright @2017 by Mr.Dong
 */
public class SupplyProfitTotalDTO implements Serializable {

	private static final long serialVersionUID = 5211496238423682189L;

	private String  telPhone;//用户账号(登陆的用户名)
	
	private Byte userType;//人员类型

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}
}
