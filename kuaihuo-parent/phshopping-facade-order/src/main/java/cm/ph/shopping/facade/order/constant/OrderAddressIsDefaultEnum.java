package cm.ph.shopping.facade.order.constant;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： description
 * @作者： 熊克文
 * @创建时间： 2017/6/22
 * @Copyright by xkw
 */
public enum OrderAddressIsDefaultEnum {

    DEFAULT(1, "默认地址"),
    NOT_DEFAULT(2, "不是默认地址");
    /**
     * 标识值：是否返积分
     */
    private Integer code;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderAddressIsDefaultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
