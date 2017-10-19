package com.ph.shopping.facade.member.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

@Table(name = "ph_traders_password")
public class TradersPassword extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2831664191951266570L;

	/**
	 * 密码类型
	 */
	@Column(name = "customerType")
	private Byte customerType;
	/**
	 * 密码
	 */
	@Column(name = "payPwd")
	private String payPwd;
	/**
	 * 密码使用者ID
	 */
	@Column(name = "userId")
	private Long userId;
	
	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public Byte getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Byte customerType) {
		this.customerType = customerType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
