package com.ph.shopping.facade.spm.dto;

import java.io.Serializable;

public class UserDrawCashDTO implements Serializable {

	private static final long serialVersionUID = -1706389768544799993L;

	public Long userId;// 用户id

	public String receiver;// 账户名

	public String bankNo;// 银行卡号

	public String bankName;// 所属银行

	public Long score;// 实际提现金额
	
	public Long drawCashScore;//提现金额

	public Byte userType;// 用户角色

	public Long handingCharge;// 提现手续费

	public String telPhone;// 提现人手机号

	public String drawcashIp; // 提现人ip地址

	private String ownerName;

	private String idCardNo;

	private String bankAbbr;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getBankAbbr() {
		return bankAbbr;
	}

	public void setBankAbbr(String bankAbbr) {
		this.bankAbbr = bankAbbr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getDrawcashIp() {
		return drawcashIp;
	}

	public void setDrawcashIp(String drawcashIp) {
		this.drawcashIp = drawcashIp;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getHandingCharge() {
		return handingCharge;
	}

	public void setHandingCharge(Long handingCharge) {
		this.handingCharge = handingCharge;
	}

	public Long getDrawCashScore() {
		return drawCashScore;
	}

	public void setDrawCashScore(Long drawCashScore) {
		this.drawCashScore = drawCashScore;
	}

}
