package com.ph.shopping.facade.member.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @ClassName: PromotionRecordVO
 * @Description:推广师 相关推广数据
 * @author: lijie
 * @date: 2017年5月2日 下午5:41:33
 * @Copyright: 2017
 */
public class PromotionRecordVO implements Serializable {

	/**
	 * @Fields serialVersionUID : 序列ID
	 */
	private static final long serialVersionUID = -1488481133020106066L;
	/**
	 * 推广师账号
	 */
	private String telPhone;
	/**
	 * 三方账号
	 */
	private String account;
	/**
	 * 推广师名称
	 */
	private String memberName;
	/**
	 * 企业账号
	 */
	private String companyAccount;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 企业类型名称
	 */
	private String companyTypeName;
	/**
	 * 企业类型ID
	 */
	private Long companyTypeId;
	/**
	 * 推广师ID
	 */
	private Long promotionId;
	/**
	 * 推广时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 推广师类型
	 */
	private Byte accountType;
	/**
	 * 序号
	 */
	private Integer num;
	/**
	 * 企业状态
	 */
	private Byte companyStatus;
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Byte getAccountType() {
		return accountType;
	}

	public void setAccountType(Byte accountType) {
		this.accountType = accountType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTypeName() {
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	public Long getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(Long companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Byte getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(Byte companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

}
