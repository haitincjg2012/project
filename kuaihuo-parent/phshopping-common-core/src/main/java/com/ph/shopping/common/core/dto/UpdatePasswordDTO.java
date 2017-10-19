package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @since 1.8
 **/
public class UpdatePasswordDTO extends BaseValidate {

    @NotNull(message = "手机号不能为空")
    private String phone	;//	用户手机号

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;//	客服号码

    @NotNull(message = "编码不能为空")
    private String codeType ;


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

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
