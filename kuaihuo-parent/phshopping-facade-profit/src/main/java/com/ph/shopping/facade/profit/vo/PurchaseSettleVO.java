package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 供应链结算VO
* @ClassName: PurchaseSettleVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月9日 下午3:21:57
 */
public class PurchaseSettleVO implements Serializable {

	private static final long serialVersionUID = 8336618752321504309L;

	private Long  id;//线下订单主键
	
	private String orderNo;//订单号
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date orderTime;//订单支付时间
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date receiptTime;//收货时间
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date settleTime;//结算时间
	
	private Long orderMoney;//订单金额
	
	private Long  settleMoney;//结算金额
	
	private Byte payType;//支付方式： 0-余额支付 1-第三方支付
		
	private String purchaserTel;//进货人手机号(商户、代理)  采购商
	
	private String purchaserName;//进货人名称
	
	private String senderTel;//发货人手机号(代理、供应商)  供货者
	
	private String senderName;//发货人名称
	
	private byte isSettle;//是否结算
	
	private Double orderMoney1;//订单金额
	
	private Double  settleMoney1;//结算金额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getSettleMoney() {
		return settleMoney;
	}

	public void setSettleMoney(Long settleMoney) {
		this.settleMoney = settleMoney;
	}

	public Byte getPayType() {
		return payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

	public String getPurchaserTel() {
		return purchaserTel;
	}

	public void setPurchaserTel(String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getSenderTel() {
		return senderTel;
	}

	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public byte getIsSettle() {
		return isSettle;
	}

	public void setIsSettle(byte isSettle) {
		this.isSettle = isSettle;
	}

	public Double getOrderMoney1() {
		return orderMoney1;
	}

	public void setOrderMoney1(Double orderMoney1) {
		this.orderMoney1 = orderMoney1;
	}

	public Double getSettleMoney1() {
		return settleMoney1;
	}

	public void setSettleMoney1(Double settleMoney1) {
		this.settleMoney1 = settleMoney1;
	}
}
