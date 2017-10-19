package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单收货方式：0=自提方式；1=送货到家。
 * @作者： 张霞
 * @创建时间： 14:53 2017/5/24
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineShippingTypeEnum {
    SHIPPING_TYPE_BYSELF((byte) 0,"自提方式"),
    SHIPPING_TYPE_SENDTOHOME((byte) 1,"送货到家")
    ;
    /**
     * 标识值：线上订单收货方式
     */
    private byte shippingType;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderOnlineShippingTypeEnum(byte shippingType, String desc) {
        this.shippingType = shippingType;
        this.desc = desc;
    }

    public byte getShippingType() {
        return shippingType;
    }

    public void setShippingType(byte shippingType) {
        this.shippingType = shippingType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
