package com.ph.shopping.facade.order.service.constant;

/**
 * 订单操作类型
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public enum OrderOperateEnum {
    UPDATE_FREIGHT((byte)0,"修改物流费用"),
    UPDATE_STATUS((byte)1,"修改订单状态"),
    UPDATE_BONUS((byte)2,"修改分润状态");

    private byte type;
    private String desc;

    OrderOperateEnum(byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
