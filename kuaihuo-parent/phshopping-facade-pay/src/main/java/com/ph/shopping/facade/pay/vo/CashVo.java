package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-member
 *
 * @描述：提现VO
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年4月5日
 *
 * @Copyright @2017 by Mr.chang
 */
public class CashVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178414283671390202L;
	
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 提现积分
	 */
	private Long cashScore;
	/**
	 * 支付密码
	 */
	private String payPwd;
	/**
	 * 手机验证码
	 */
	private String msgCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCashScore() {
		return cashScore;
	}
	public void setCashScore(Long cashScore) {
		this.cashScore = cashScore;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

}
