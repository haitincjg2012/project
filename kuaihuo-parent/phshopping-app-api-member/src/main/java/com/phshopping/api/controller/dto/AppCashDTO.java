package com.phshopping.api.controller.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: CashDTO
 * @Description:获取积分提现页面数据传输dto
 * @author: 李杰
 * @date: 2017年5月22日 下午10:09:28
 * @Copyright: 2017
 */
public class AppCashDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -7675434956119298972L;
	/**
	 * 会员ID
	 */
	private Long memberId;
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
	private String smsCode;
	/**
	 * 会员手机号
	 */
	private String telPhone;
	/**
	 * 请求标识
	 */
	private String token;

	/**
	 * member标识
	 */
	private String memberToken;

	/**
	 * 提现类型（推广  商户/会员   提现）
	 */
	private String transCode;

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMemberToken() {
		return memberToken;
	}

	public void setMemberToken(String memberToken) {
		this.memberToken = memberToken;
	}
}
