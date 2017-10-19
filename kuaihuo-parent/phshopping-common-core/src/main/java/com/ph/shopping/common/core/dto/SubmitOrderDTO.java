package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @since 1.8
 **/
public class SubmitOrderDTO extends BaseValidate {

    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
