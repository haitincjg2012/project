package com.ph.shopping.facade.member.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行卡关联开户银行表
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
@Table(name="ph_bank_codename_data")
public class BankCodenameData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077321821762619569L;
	/**
	 * id
	 */
	@Id
	private Long id;
	/**
	 * 银行卡号
	 */
	@Column(name = "bankCode")
	private String bankCode;
	/**
	 * 银行卡名称
	 */
	@Column(name = "bankName")
	private String bankName;
	/**
	 * 创建时间
	 */
	@Column(name="createTime")
	private Date createTime;
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
