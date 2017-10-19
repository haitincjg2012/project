package com.phshopping.api.controller.dto;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;

import com.ph.shopping.common.core.base.BaseEntityForToken;

public class ShopCartDTO extends BaseEntityForToken implements Serializable {

	private static final long serialVersionUID = -1321597620742554113L;

	private Long memberid;
	private Long dishid;
	private Long type;
	private Long num;
	private String token;
	private Long merchantId;
	private String hopetime;
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public Long getDishid() {
		return dishid;
	}

	public void setDishid(Long dishid) {
		this.dishid = dishid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getHopetime() {
		return hopetime;
	}

	public void setHopetime(String hopetime) {
		this.hopetime = hopetime;
	}

}
