package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @since 1.8
 **/
public class ResetPasswordDTO extends BaseValidate {

    @NotNull(message = "手机号不能为空")
    private String phone	;//	用户手机号

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;//	客服号码

    @NotNull(message = "密码不能为空")
    private String password ;

    @NotNull(message = "角色不能为空")
    private String role ;// 用户/掌柜/市级代理/区县代理


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
