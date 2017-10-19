package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**‘
 * 代理商/会员注册
 * @author xwolf
 * @since 1.8
 **/
public class RegisterDTO extends BaseValidate {

    @NotNull(message = "登录账号不能为空")
    private String userName;//	登录账号

    @NotNull(message = "登录密码不能为空")
    private String password; //登录密码

    @NotNull(message = "用户手机号不能为空")
    private String phone;    //	用户手机号

    @NotNull(message = "申请角色不能为空")
    private String role;  //申请角色（代理/业务员）

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;  //客服号码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
