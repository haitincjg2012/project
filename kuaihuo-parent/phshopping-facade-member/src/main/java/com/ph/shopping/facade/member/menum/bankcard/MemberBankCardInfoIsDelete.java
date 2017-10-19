package com.ph.shopping.facade.member.menum.bankcard;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： description
 * @作者： 熊克文
 * @创建时间： 2017/5/26
 * @Copyright by xkw
 */
public enum MemberBankCardInfoIsDelete {
    DELETE((byte) 1, "已删除"),
    NOT_DELETE((byte) 2, "未删除");

    /**
     * 编码
     */
    private Byte code;
    /**
     * 描述
     */
    private String remark;

    MemberBankCardInfoIsDelete(Byte code, String remark) {
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
