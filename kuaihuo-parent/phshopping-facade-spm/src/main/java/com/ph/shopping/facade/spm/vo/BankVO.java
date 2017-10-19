package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

public class BankVO implements Serializable {

	private static final long serialVersionUID = 2723452481692987849L;

	private String ownerName;// 姓名
	private String bankName;// 银行名称
	private String cardNo;// 卡号

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

}
