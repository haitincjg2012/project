package com.ph.shopping.facade.member.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @项目：phshopping-facade-member
 * @描述：推广师VO
 * @作者： Mr.Shu
 * @创建时间：2017-05-19
 * @Copyright @2017 by Mr.Shu
 */

public class MemberPromotionVO implements Serializable {

	
	private static final long serialVersionUID = 5522164344797681324L;

	private Long id;

	/**
	 * 三方账号
	 */
	private String account;

	/**
	 * 账户类型：1：新业态推广师 2 ：本地电商推广师
	 */
	private Byte accountType;

	/**
	 * 推广师证图片
	 */
	private String url;

	/**
	 * 推广师账号（会员手机号）
	 */
	private String telPhone;
	/**
	 * 推广师姓名（会员名）
	 */
	private String memberName;
	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 状态 1：待审核 2：审核通过 3：审核未通过
	 */
	private Byte status;

	/**
	 * 申请时间（创建时间）
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 会员ID
	 */
	private Long memberId;

	/**
	 * 审核时间=
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date checkTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Byte getAccountType() {
		return accountType;
	}

	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
}
