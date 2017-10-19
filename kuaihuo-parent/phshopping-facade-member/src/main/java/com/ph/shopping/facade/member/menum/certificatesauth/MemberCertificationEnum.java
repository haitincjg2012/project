package com.ph.shopping.facade.member.menum.certificatesauth;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 用户实名认证枚举
 * @作者： 熊克文
 * @创建时间： 2017/5/24
 * @Copyright by xkw
 */
public enum MemberCertificationEnum {
    NOT_CERTIFIED((byte) 1, "未认证"),
    VERIFIED((byte) 2, "已认证"),
    PENDING_REVIEW((byte) 3, "待审核");

    /**
     * 编码
     */
    private Byte code;
    /**
     * 描述
     */
    private String remark;

    MemberCertificationEnum(Byte code, String remark) {
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
