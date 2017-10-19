package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.common.core.customenum.TransCodeEnum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 积分支付DTO
 *
 * @author 郑朋
 * @create 2017/6/19
 **/
public class PayScoreDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = 4775253387199056558L;

    /**
     * 会员id
     */
    @NotNull
    private Long memberId;

    /**
     * 订单号
     */
    @NotNull
    private String orderNo;

    /**
     * 交易类型
     */
    @NotNull
    private TransCodeEnum transCodeEnum;

    /**
     * 支付金额
     */
    @NotNull
    private String amount;

    /**
     * 订单id
     */
    @NotNull
    private Long orderId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public TransCodeEnum getTransCodeEnum() {
        return transCodeEnum;
    }

    public void setTransCodeEnum(TransCodeEnum transCodeEnum) {
        this.transCodeEnum = transCodeEnum;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
