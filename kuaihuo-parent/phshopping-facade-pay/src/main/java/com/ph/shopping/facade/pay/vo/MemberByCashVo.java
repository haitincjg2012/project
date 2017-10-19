package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-member
 *
 * @描述：会员提现用户详情VO
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年4月5日
 *
 * @Copyright @2017 by Mr.chang
 */
public class MemberByCashVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178414283671390202L;
	
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 会员手机号
	 */
	private String phone;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 支付密码
	 */
	private String payPwd;
	
	/**
	 * 可用积分
	 */
	private Long enableScore;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getPayPwd() {
		return payPwd;
	}
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getEnableScore() {
		return enableScore;
	}
	public void setEnableScore(Long enableScore) {
		this.enableScore = enableScore;
	}

}
