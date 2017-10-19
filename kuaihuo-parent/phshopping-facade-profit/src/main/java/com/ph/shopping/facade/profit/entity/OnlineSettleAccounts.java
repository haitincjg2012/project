package com.ph.shopping.facade.profit.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OnlineSettleAccounts implements Serializable {
	private static final long serialVersionUID = 1805044469195815130L;

	/** 订单号 */
	private String orderNo;

	/** 订单金额 */
	private BigDecimal orderMoney;

	/** 订单时间(下单时间) */
	private Date deliveryTime;

	/** 收货时间 */
	private Date acceptTime;

	/** 结算金额 */
	private BigDecimal settleMoney;

	/** 支付方式(0:积分支付 1:微信 2:支付宝 3:银行卡) */
	private Byte payType;

	/** 会员手机号 */
	private String telPhone;

	/** 会员名称 */
	private String memberName;

	/** 企业手机号(供应商手机号) */
	private String personTel;

	/** 企业名称(供应商名称) */
	private String supplierName;

	/** 创建人 */
	private Long createrId;

	/** 供应商id */
	private Long supplierId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public BigDecimal getSettleMoney() {
		return settleMoney;
	}

	public void setSettleMoney(BigDecimal settleMoney) {
		this.settleMoney = settleMoney;
	}

	public Byte getPayType() {
		return payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone == null ? null : telPhone.trim();
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName == null ? null : memberName.trim();
	}

	public String getPersonTel() {
		return personTel;
	}

	public void setPersonTel(String personTel) {
		this.personTel = personTel == null ? null : personTel.trim();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName == null ? null : supplierName.trim();
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}