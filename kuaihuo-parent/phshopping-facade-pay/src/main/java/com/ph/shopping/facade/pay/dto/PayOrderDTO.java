package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @项目：phshopping-parent
 * @描述：订单支付DTO
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang
 */
public class PayOrderDTO extends BaseValidate implements Serializable{

    private static final long serialVersionUID = 1000036258570251131L;

    /**
     * 商品支付订单号
     */
    @NotNull
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
