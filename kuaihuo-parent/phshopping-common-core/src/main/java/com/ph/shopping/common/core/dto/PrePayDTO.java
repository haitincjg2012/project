package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * 预定订单通知
 * @author xwolf
 * @since 1.8
 **/
public class PrePayDTO extends BaseValidate{

    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "订单号不能为空")
    private String orderNum;

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
