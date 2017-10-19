package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单状态值：0=待付款；1=待发货；2=待收货；3=交易完成；4=交易取消
 * @作者： 张霞
 * @创建时间： 14:44 2017/5/24
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineStatusEnum {
    STATUS_TODO_PAY((byte)0,"待付款"),
    STATUS_TODO_SEND((byte)1,"待发货"),
    STATUS_TODO_RECEIVED((byte)2,"待收货"),
    STATUS_DONE_ORDER((byte)3,"交易完成"),
    STATUS_CANCEL_ORDER((byte)4,"交易取消")
    ;
    /**
     * 标识值：线上订单状态
     */
    private byte status;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderOnlineStatusEnum(byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
