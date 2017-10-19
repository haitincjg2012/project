package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: PayVO
 * @Description: 充值VO
 * @author 王强
 * @date 2017年6月15日 下午4:41:49
 */
public class PayVO implements Serializable {
	private static final long serialVersionUID = 7991977400849711838L;
	private String orderNum;
	private String amount;
	private String description;
	private String payUrl;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
}
