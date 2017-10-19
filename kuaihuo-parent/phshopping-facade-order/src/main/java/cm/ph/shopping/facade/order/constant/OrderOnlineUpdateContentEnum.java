package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：更新订单的具体内容的类型:0=更新订单是否分润,1=更新订单是否结算,2=更新订单状态
 * @作者： 张霞
 * @创建时间： 15:20 2017/6/1
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineUpdateContentEnum {
    ONLINE_UPDATE_STATUS((byte) 2,"更新订单状态"),
    ONLINE_UPDATE_ISSETTLE((byte) 1,"更新订单是否结算"),
    ONLINE_UPDATE_ISBACKSCORE((byte) 0,"更新订单是否分润");
    private byte type;
    private String desc;

    OrderOnlineUpdateContentEnum(byte type, String desc) {
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
