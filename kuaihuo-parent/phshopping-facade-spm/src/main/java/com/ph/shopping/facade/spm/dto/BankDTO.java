package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

public class BankDTO implements Serializable {

	private static final long serialVersionUID = -1594141118563189489L;
	
	private String ownerName;// 姓名
	private String bankName;// 银行名称
	private String cardNo;// 卡号
	private String telPhone;//手机号
	private int operator;//1新增，2编辑，3删除

	private Long userId; //用户id
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

}
