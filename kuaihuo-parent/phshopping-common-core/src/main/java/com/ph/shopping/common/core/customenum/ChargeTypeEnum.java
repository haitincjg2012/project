package com.ph.shopping.common.core.customenum;

/**
 * @项目：phshopping-facade-order
 * @描述：充值方式:0银行卡充值,1支付宝充值
 * @作者： 张霞
 * @创建时间： 18:37 2017/7/12
 * @Copyright @2017 by zhangxia
 */
public enum ChargeTypeEnum {
    CHARGE_TYPE_BANK((byte)0,"银行卡充值"),
    CHARGE_TYPE_ALIPAY((byte)1,"支付宝充值")
    ;
    /**
     * 标识值:充值方式
     */
    private byte chargeType;
    /**
     *标识描述信息
     */
    private String desc;

    ChargeTypeEnum(byte chargeType, String desc) {
        this.chargeType = chargeType;
        this.desc = desc;
    }

    public byte getChargeType() {
        return chargeType;
    }

    public void setChargeType(byte chargeType) {
        this.chargeType = chargeType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
