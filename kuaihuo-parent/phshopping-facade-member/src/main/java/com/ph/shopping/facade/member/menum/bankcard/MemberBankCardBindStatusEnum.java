package com.ph.shopping.facade.member.menum.bankcard;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员银行卡绑定状态枚举
 * @作者： 熊克文
 * @创建时间： 2017/5/26
 * @Copyright by xkw
 */
public enum MemberBankCardBindStatusEnum {
    BIND_ED((byte) 1, "已绑定"),
    UN_BOUND_ED((byte) 2, "未绑定");

    /**
     * 编码
     */
    private Byte code;
    /**
     * 描述
     */
    private String remark;

    MemberBankCardBindStatusEnum(Byte code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
