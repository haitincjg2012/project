package cm.ph.shopping.facade.order.constant;

/**
 * 退款申请订单枚举
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
public enum RefundStatusEnum {
    PASS((byte)0,"通过"),
    UN_PASS((byte)1,"未通过"),
    PENDING_AUDIT((byte)2,"待审核");

    private byte status;
    private String desc;

    RefundStatusEnum(byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
