package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：订单是否已返积分，是否已返积分状态值：0=未返；1=已返
 * @作者： 张霞
 * @创建时间： 13:57 2017/5/23
 * @Copyright @2017 by zhangxia
 */
public enum OrderIsBackScoreEnum {
    IS_BACK_SCORE_NOT((byte)0,"未返积分"),
    IS_BACK_SCORE((byte)1,"已返积分");
    /**
     * 标识值：是否返积分
     */
    private byte isBackScore;
    /**
     *标识描述信息
     */
    private String desc;

    OrderIsBackScoreEnum(byte isBackScore, String desc) {
        this.isBackScore = isBackScore;
        this.desc = desc;
    }

    public byte getIsBackScore() {
        return isBackScore;
    }

    public void setIsBackScore(byte isBackScore) {
        this.isBackScore = isBackScore;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
