package com.ph.shopping.facade.spm.request;

import java.io.Serializable;

import com.ph.shopping.common.core.customenum.TransCodeEnum;

public class UserBalanceRequest implements Serializable {
    private static final long serialVersionUID = 4340240429593991490L;
    private Long memberId;
	private TransCodeEnum codeEnum;
	private Integer type;
	private double balance;
	
	private long money;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public TransCodeEnum getCodeEnum() {
		return codeEnum;
	}

	public void setCodeEnum(TransCodeEnum codeEnum) {
		this.codeEnum = codeEnum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
}
