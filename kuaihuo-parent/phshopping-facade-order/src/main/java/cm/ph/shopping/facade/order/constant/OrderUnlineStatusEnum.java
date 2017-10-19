package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单状态值：0=待付款；1=付款中(第三方支付情况)；2=交易完成；3=交易取消（商户取消）
 * @作者： 张霞
 * @创建时间： 22:21 2017/5/23
 * @Copyright @2017 by zhangxia
 */
public enum OrderUnlineStatusEnum {
    STATUS_TODO_PAY((byte) 0,"待付款"),
    STATUS_DOING_PAY((byte)1,"付款中"),
    STATUS_DONE_ORDER((byte) 2,"交易完成"),
    STATUS_CANCEL_ORDER((byte)3,"交易取消");
    /**
     * 标识值：线下订单状态
     */
    private byte status;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderUnlineStatusEnum(byte status, String desc) {
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
