package com.ph.shopping.facade.pay.enums;

/**
 * 支付方式（支付平台）
 *
 * @author 郑朋
 * @create 2017/5/18
 **/
public enum PaymentEnum {
    THIRD((byte) 1,"银行");

    private byte code;
    private String name;

    PaymentEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
