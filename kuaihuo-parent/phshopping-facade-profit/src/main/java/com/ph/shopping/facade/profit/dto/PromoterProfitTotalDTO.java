package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: PromoterProfitTotalDTO
* @Description: TODO(推广师分润总表DTO)
* @author Mr.Dong
* @date 2017年6月6日 上午10:55:26
 */
public class PromoterProfitTotalDTO implements Serializable{

	private static final long serialVersionUID = -5471909129666546282L;

	private String telPhone;//推广师手机号
	
	private String memberName;//推广师姓名

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	
}
