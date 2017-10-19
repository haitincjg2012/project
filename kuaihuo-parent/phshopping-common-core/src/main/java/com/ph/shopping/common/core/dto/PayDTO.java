package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * 订单支付通知
 * @author xwolf
 * @since 1.8
 **/
public class PayDTO extends BaseValidate {

    @NotNull(message = "手机号不能为空")
    private String phone	;//	用户手机号

    @NotNull(message = "订单号不能为空")
    private String orderNum	;//	订单号

    @NotNull(message = "订单金额不能为空")
    private String money	;//	订单金额

    @NotNull(message = "订单商店地址不能为空")
    private String address	;//	订单商店地址

    @NotNull(message = "订单商店电话不能为空")
    private String mermentNum	;//	订单商店电话

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;//	客服号码


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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMermentNum() {
        return mermentNum;
    }

    public void setMermentNum(String mermentNum) {
        this.mermentNum = mermentNum;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
