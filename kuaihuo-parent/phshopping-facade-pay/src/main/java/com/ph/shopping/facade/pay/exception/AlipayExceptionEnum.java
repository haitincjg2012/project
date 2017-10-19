package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @项目：phshopping-parent
 * @描述：支付宝异常码
 * @作者： Mr.chang
 * @创建时间：2017/6/2
 * @Copyright @2017 by Mr.chang
 */
public enum  AlipayExceptionEnum implements RespCode {

    RESPONSE_SUCCESS("200","业务处理成功"),
    PARAM_ERROR("160001","参数校验错误"),
    ALIPAY_SIGN_ERROR("160002","支付宝签名错误"),
    ALIPAY_REFUND_ERROR("160003","支付宝退款错误"),
    ALIPAY_AMOUNT_ERROR("160004","支付宝回调金额错误");

    AlipayExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
