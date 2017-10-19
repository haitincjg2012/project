package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @since 1.8
 **/
public class CashDTO extends BaseValidate {

    @NotNull(message = "手机号码不能为空")
    private String phone	;//	用户手机号

    /**
     * Fr170002:现金/积分支付标识码
     * @see com.ph.shopping.common.core.constant.SmsCodeTypeEnum
     */
    private String codeType = "Fr170002";

    @NotNull(message = "操作不能为空")
    private String operation;	//	执行注册操作/找回密码操作

    @NotNull(message = "有效期不能为空")
    private String YTime;	//	有效期

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;	//	客服号码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getYTime() {
        return YTime;
    }

    public void setYTime(String YTime) {
        this.YTime = YTime;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
