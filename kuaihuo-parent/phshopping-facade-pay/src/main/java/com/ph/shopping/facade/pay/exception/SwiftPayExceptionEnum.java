package com.ph.shopping.facade.pay.exception;

import com.ph.shopping.common.util.core.RespCode;

/**
 * @项目：phshopping-facade-order
 * @描述：威富通扫码支付异常码
 * @作者： 张霞
 * @创建时间： 17:54 2017/7/20
 * @Copyright @2017 by zhangxia
 */
public enum SwiftPayExceptionEnum implements RespCode {
    SWIFT_PAY_YANQIAN_FAIL("180001","验签失败"),
    SWIFT_PAY_RESPONSE_CONTENT("180002","请求第三方接口返回结果有误，缺少sign内容"),
    SWIFT_PAY_RESPONSE_FAIL("180003","请求第三方接口响应有误"),
    SWIFT_PAY_AMOUNT_ERROR("180004","威富通扫码支付回调金额错误")
    ;


    private String code;
    private String msg;

    SwiftPayExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
