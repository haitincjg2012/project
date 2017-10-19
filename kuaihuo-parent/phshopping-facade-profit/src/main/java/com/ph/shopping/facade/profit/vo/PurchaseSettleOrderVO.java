package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
* 供应链结算的订单
* @ClassName: PurchaseSettleOrderVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月11日 下午12:19:35
*/
public class PurchaseSettleOrderVO implements Serializable {
	
	private static final long serialVersionUID = 8838329820502933659L;

	private Long  id ;//供应链订单表主键
	
	private Long   settleMoney;//需要结算的金额
	
	private Long  senderId;//结算金额对应的供货方(发货方)
	
	private String orderNo;//订单号

	private Byte purchaseType;//进货单类型（0=商户进货；1=代理商进货）
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSettleMoney() {
		return settleMoney;
	}

	public void setSettleMoney(Long settleMoney) {
		this.settleMoney = settleMoney;
	}


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Byte getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Byte purchaseType) {
		this.purchaseType = purchaseType;
	}

}
