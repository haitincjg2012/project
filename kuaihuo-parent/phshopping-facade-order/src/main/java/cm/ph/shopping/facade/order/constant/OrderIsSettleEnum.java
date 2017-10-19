package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：订单(包含线上、线下)是否结算(即是否分润)
 * @作者： 张霞
 * @创建时间： 22:11 2017/5/23
 * @Copyright @2017 by zhangxia
 */
public enum  OrderIsSettleEnum {
    IS_SETTLE_NOT((byte)0,"未计算"),
    IS_SETTLE((byte)1,"已计算");
    /**
     * 标识值：是否结算(分润)
     */
    private byte isSettle;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderIsSettleEnum(byte isSettle, String desc) {
        this.isSettle = isSettle;
        this.desc = desc;
    }

    public byte getIsSettle() {
        return isSettle;
    }

    public void setIsSettle(byte isSettle) {
        this.isSettle = isSettle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
