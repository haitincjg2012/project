package com.ph.shopping.facade.member.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: UnbundBankCardInfoVo  
* @Description: 解绑使用数据  
* @author tony  
* @date 2017年3月15日  
*
 */
public class UnbundBankCardInfoDTO implements Serializable{

	/**  
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)  
	*/  
	private static final long serialVersionUID = -7032835304937114798L;

	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 银行卡号码
	 */
	private String bankCardNo;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	
}
