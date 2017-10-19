package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @date 2017-08-29 14:50
 * @since 1.8
 **/
public class CheckDTO extends BaseValidate{

    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotNull(message = "验证码不能为空")
    private String code;

    private String codeType;//代码类型 对应指定短信接口的codeType

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
