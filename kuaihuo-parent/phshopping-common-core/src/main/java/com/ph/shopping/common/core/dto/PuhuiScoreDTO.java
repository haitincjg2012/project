package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author xwolf
 * @date 2017-09-01 11:07
 * @since 1.8
 **/
public class PuhuiScoreDTO extends BaseValidate {

    @NotNull(message = "订单号不能为空")
    private String orderNo;//订单号(业务系统订单号)

    @NotNull(message = "订单金额不能为空")
    private BigDecimal orderMoney;//订单金额

    @NotNull(message = "Token不能为空")
    private String token;

    @NotNull(message = "积分类型不能为空")
    private Integer sourceType;// 来源(0:天天返 4:红包)


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}
