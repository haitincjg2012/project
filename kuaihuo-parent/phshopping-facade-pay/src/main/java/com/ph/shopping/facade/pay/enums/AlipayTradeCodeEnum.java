package com.ph.shopping.facade.pay.enums;

/**
 * @项目：phshopping-parent
 * @描述：支付宝回调状态Enum
 * @作者： Mr.chang
 * @创建时间：2017/5/26
 * @Copyright @2017 by Mr.chang
 */
public enum AlipayTradeCodeEnum {

    TRADE_CLOSED("TRADE_CLOSED","未付款交易超时关闭,或支付完成后全额退款"),
    TRADE_SUCCESS("TRADE_SUCCESS","交易支付成功"),
    TRADE_FINISHED("TRADE_FINISHED","交易结束,不可退款");

    private String code;
    private String name;

    AlipayTradeCodeEnum(String code, String name) {
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
