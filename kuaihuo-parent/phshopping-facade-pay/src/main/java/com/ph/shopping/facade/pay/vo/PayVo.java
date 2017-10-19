package com.ph.shopping.facade.pay.vo;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-pay
 * @描述：支付入参vo
 * @作者： 张霞
 * @创建时间： 9:46 2017/3/25
 * @Copyright @2017 by zhangxia
 */
public class PayVo implements Serializable{
    private static final long serialVersionUID = -5195280967436667731L;
    
    /**
     * 商品支付订单号
     */
    private String orderNum;
    /**
     * 充值或者支付金额
     */
    private String amount;
    /**
     * 备注
     */
    private String description;
    
	public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
