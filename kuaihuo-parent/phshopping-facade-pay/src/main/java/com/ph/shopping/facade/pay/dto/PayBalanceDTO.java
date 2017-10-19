package com.ph.shopping.facade.pay.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.common.core.customenum.TransCodeEnum;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 余额支付DTO
 *
 * @author 郑朋
 * @create 2017/6/5
 **/
public class PayBalanceDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = 8025624515216987790L;

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

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
     * 用户类型
     */
    private Byte userType;

    /**
     * 支付金额
     */
    @NotNull
    private String amount;

    /**
     * 订单id
     */
    private Long orderId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
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
