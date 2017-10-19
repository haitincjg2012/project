package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;

/**
 * 线下结算订单VO(获取需要结算的线下订单)
* @ClassName: UnLineSettleOrderVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月8日 上午10:00:33
 */
public class UnLineSettleOrderVO implements Serializable {
	
	private static final long serialVersionUID = -6759907276472477670L;
	
	private Long  id ;//线下订单表主键
	
	private Long   settleMoney;//需要结算的金额
	
	private Long  merchantId;//结算金额对应的商户
	
	private String orderNo;//订单号

	private Byte payType;//支付类型

	public Byte getPayType() {
		return payType;
	}

	public void setPayType(Byte payType) {
		this.payType = payType;
	}

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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
