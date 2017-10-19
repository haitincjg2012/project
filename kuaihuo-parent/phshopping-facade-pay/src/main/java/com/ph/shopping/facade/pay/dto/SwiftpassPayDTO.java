package com.ph.shopping.facade.pay.dto;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseValidate;

/**
 * @项目：phshopping-facade-pay
 * @描述：威富通扫码支付DTO
 * @作者： 张霞
 * @创建时间： 14:45 2017/7/20
 * @Copyright @2017 by zhangxia
 */
public class SwiftpassPayDTO extends BaseValidate {

    private static final long serialVersionUID = 1009036258570251131L;

    /**
     * 订单号--out_trade_no
     */
    @NotBlank(message="订单号不能为空")
    private String orderNo;
    /**
     * 商品描述--body
     */
    @NotBlank(message="商品描述不能为空")
    private String body;
    /**
     * 订单金额(分为单位)--total_fee
     */
    @NotNull(message="订单金额不能为空")
    private Double money;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

}
