package com.ph.shopping.facade.member.menum.certificatesauth;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 证件认证状态枚举
 * @作者： 熊克文
 * @创建时间： 2017/5/24
 * @Copyright by xkw
 */
public enum MemberCertificatesStatusEnum {
    SUCCESS((byte) 1, "认证成功"),
    FAIL((byte) 0, "认证失败");

    /**
     * 编码
     */
    private Byte code;
    /**
     * 描述
     */
    private String remark;

    MemberCertificatesStatusEnum(Byte code, String remark) {
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
