package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class MemberInfoByCashVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178414283671390202L;
	
	/**
	 * ID
	 */
	private Long id;

	private String memberName;
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
	private String idCardNo;
	/**
	 * 可用积分
	 */
	private BigDecimal score;
	/**
	 * 提现积分
	 */
	@JsonIgnore
	private Long cashScore;
	/**
	 * 支付密码
	 */
	@JsonIgnore
	private String payPwd;
	/**
	 * 手机验证码
	 */
	@JsonIgnore
	private String msgCode;
	
	@JsonIgnore
	private String phone;

	/**
	 * 是否已实名认证 : 1、未认证 2、已认证  3、待审核
	 */
	private Byte certification;

	/**
	 * 银行卡名称
	 */
	private String bankName;


	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

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

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Byte getCertification() {
		return certification;
	}

	public void setCertification(Byte certification) {
		this.certification = certification;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
