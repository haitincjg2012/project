package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线上子订单申请退款申请状态 0=退款审核中；1=拒绝退款；2=退款中；3=退款完成
 * @作者： 张霞
 * @创建时间： 23:29 2017/5/23
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineAppliStatusEnum {
    APPLISTATUS_AUDITING((byte) 0,"退款审核中"),
    APPLISTATUS_REFUND_REFUSE((byte) 1,"拒绝退款"),
    APPLISTATUS_REFUNDING((byte) 2,"退款中"),
    APPLISTATUS_REFUNDED((byte) 3,"退款完成"),
    ;
    /**
     * 标识值：申请退款状态
     */
    private byte appliStatus;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderOnlineAppliStatusEnum(byte appliStatus, String desc) {
        this.appliStatus = appliStatus;
        this.desc = desc;
    }

    public byte getAppliStatus() {
        return appliStatus;
    }

    public void setAppliStatus(byte appliStatus) {
        this.appliStatus = appliStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
