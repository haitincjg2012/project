package com.ph.shopping.common.core.customenum;

/**
 * @项目：phshopping-facade-order
 * @描述：系统中所有支付方式规范
 * @作者： 张霞
 * @创建时间： 13:47 2017/6/15
 * @Copyright @2017 by zhangxia
 */
public enum PayTypeEnum {
    PAY_TYPE_CASH((byte)0,"现金余额支付"),
    PAY_TYPE_SCORE((byte)1,"积分支付"),
    PAY_TYPE_ALIPAY((byte)2,"支付宝支付"),
    PAY_TYPE_WEIXINPAY((byte)3,"微信支付"),
    PAY_TYPE_YILIANPAY((byte)4,"易联支付"),
    PAY_TYPE_COMMONPAY((byte)5,"北京通用支付"),
    PAY_TYPE_SHORTCUT((byte)6,"快捷支付"),
    PAY_TYPE_APP((byte)7,"北京通用手机支付");
    /**
     * 标识值:支付方式
     */
    private byte payType;
    /**
     *标识描述信息
     */
    private String desc;

    PayTypeEnum(byte payType, String desc) {
        this.payType = payType;
        this.desc = desc;
    }

    public byte getPayType() {
        return payType;
    }

    public void setPayType(byte payType) {
        this.payType = payType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
