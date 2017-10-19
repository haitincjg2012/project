package com.phshopping.api.controller.dto;

import java.io.Serializable;

public class AppBankCardBindInfoDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 201761334995409351L;
	/**
	 * 银行卡号
	 */
	private String cardNum;
	/**
	 * 开户银行
	 */
	private String bankName;
	/**
	 * token
	 */
	private String token;
	/**
	 * 开户人姓名
	 */
	private String ownName;
	/**
	 * 卡数据ID
	 */
	private Long bankDataId;
	
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOwnName() {
		return ownName;
	}
	public void setOwnName(String ownName) {
		this.ownName = ownName;
	}
	public Long getBankDataId() {
		return bankDataId;
	}
	public void setBankDataId(Long bankDataId) {
		this.bankDataId = bankDataId;
	}
	
}
