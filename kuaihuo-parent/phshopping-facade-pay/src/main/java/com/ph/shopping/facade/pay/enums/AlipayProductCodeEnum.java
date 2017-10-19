package com.ph.shopping.facade.pay.enums;

/**
 * @项目：phshopping-parent
 * @描述：支付宝签约产品CodeEnum
 * @作者： Mr.chang
 * @创建时间：2017/5/26
 * @Copyright @2017 by Mr.chang
 */
public enum AlipayProductCodeEnum {

    PRODUCT_FAST_INSTANT_CODE("FAST_INSTANT_TRADE_PAY","支付宝网页即时到账支付产品码"),
    QUICK_MSECURITY_PAY("QUICK_MSECURITY_PAY","支付宝APP支付产品码");

    private String code;
    private String name;

    AlipayProductCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
