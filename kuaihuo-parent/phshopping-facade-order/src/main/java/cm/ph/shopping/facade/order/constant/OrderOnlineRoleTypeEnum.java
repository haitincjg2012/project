package cm.ph.shopping.facade.order.constant;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单状态变更记录的 操作人类型:线上订单只有会员、平台(定时器)和供应商操作):0=会员；1=供应商；2=定时器。备注：角色为定时器时，对应的创建人id统一为0
 * @作者： 张霞
 * @创建时间： 8:56 2017/5/24
 * @Copyright @2017 by zhangxia
 */
public enum OrderOnlineRoleTypeEnum {
    ROLE_TYPE_MEMBER((byte) 0,"会员"),
    ROLE_TYPE_SUPPLIER((byte) 1,"供应商"),
    ROLE_TYPE_TIMER((byte) 2,"定时器"),
    ROLE_TYPE_BANK((byte) 3,"银行订单过期");
    /**
     * 标识值：线上订单变更记录的创建人角色
     */
    private byte roleType;
    /**
     * 标识描述信息
     */
    private String desc;

    OrderOnlineRoleTypeEnum(byte roleType, String desc) {
        this.roleType = roleType;
        this.desc = desc;
    }

    public byte getRoleType() {
        return roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
