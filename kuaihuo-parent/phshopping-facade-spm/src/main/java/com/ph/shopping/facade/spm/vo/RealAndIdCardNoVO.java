package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

public class RealAndIdCardNoVO implements Serializable {
	private static final long serialVersionUID = -7879258421427749329L;
	private String ownerName;// 名称
	private String idCardNo;// 身份证
	
	private int isAuth; //是否认证0未认证，1已认证
	
	private Long bankCardInfoId;//认证信息主键

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

	public int getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(int isAuth) {
		this.isAuth = isAuth;
	}

	public Long getBankCardInfoId() {
		return bankCardInfoId;
	}

	public void setBankCardInfoId(Long bankCardInfoId) {
		this.bankCardInfoId = bankCardInfoId;
	}
}
