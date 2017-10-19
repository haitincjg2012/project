package cm.ph.shopping.facade.order.constant;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： description
 * @作者： 熊克文
 * @创建时间： 2017/6/22
 * @Copyright by xkw
 */
public enum OrderAddressStatusEnum {

    DELETE((byte) 1, "删除"),
    NORMAL((byte) 2, "正常");
    /**
     * 标识值：是否返积分
     */
    private Byte code;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderAddressStatusEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
