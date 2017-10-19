package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * phshopping-facade-profit
 *
 * @description：供应链订单分润总表VO
 *
 * @author：Mr.Dong
 *
 * @createTime：2017年5月16日
 *
 * @Copyright @2017 by Mr.Dong
 */
public class SupplyProfitTotalVO implements Serializable {

	private static final long serialVersionUID = -8898539822999022134L;

	private String  telPhone;//用户账号
	
	private Byte userType;//人员类型
	
	private String userName;//供应链企业名称
	
	private Long profited;//已经分润
	
	private Double profited1;//已经分润

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getProfited() {
		return profited;
	}

	public void setProfited(Long profited) {
		this.profited = profited;
	}

	public Double getProfited1() {
		return profited1;
	}

	public void setProfited1(Double profited1) {
		this.profited1 = profited1;
	}
	
}
