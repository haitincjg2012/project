package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: UserBalanceVO
* @Description: 用户余额VO
* @author Mr.Dong
* @date 2017年5月5日 下午5:01:30
 */
public class UserBalanceVO implements Serializable{

	private static final long serialVersionUID = 5435342262247587454L;

	private Long manageId; //用户id
	
	private Long balance;  //用户分润得到的金额


	public Long getManageId() {
		return manageId;
	}

	public void setManageId(Long manageId) {
		this.manageId = manageId;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	
}
