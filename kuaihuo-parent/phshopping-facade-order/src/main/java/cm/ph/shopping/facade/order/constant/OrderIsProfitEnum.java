package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：订单是否已分润
 * @作者： 张霞
 * @创建时间： 20:30 2017/6/7
 * @Copyright @2017 by zhangxia
 */
public enum  OrderIsProfitEnum {
    IS_PROFIT_NOT((byte)0,"未分润"),
    IS_PROFIT((byte)1,"已分润"),
    IS_MEMBER_PROFIT((byte)2,"会员分润");
    /**
     * 标识值：是否分润(分润)
     */
    private byte isProfit;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderIsProfitEnum(byte isProfit, String desc) {
        this.isProfit = isProfit;
        this.desc = desc;
    }

    public byte getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(byte isProfit) {
        this.isProfit = isProfit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
