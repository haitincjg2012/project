package com.ph.shopping.facade.member.menum.certificatesauth;

import com.ph.shopping.facade.member.exception.MemberException;
import com.ph.shopping.facade.member.menum.MemberResultEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员证件类型枚举常量
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
public enum MemberCertificatesTypeEnum {

    ID_CARD((byte) 1, "身份证");
    /*PASSPORT(2,"护照");*/

    public static MemberCertificatesTypeEnum getEnumByCode(Byte code) {
        Optional<MemberCertificatesTypeEnum> memberCertificatesTypeEnum =
                Arrays.stream(MemberCertificatesTypeEnum.values()).filter(_memberCertificatesTypeEnum -> _memberCertificatesTypeEnum.getCode().equals(code)).findFirst();
        if (memberCertificatesTypeEnum.isPresent()) {
            return memberCertificatesTypeEnum.get();
        } else {
            throw new MemberException(MemberResultEnum.CERTIFICATE_TYPE_WRONG.getMsg());
        }

    }


    /**
     * 编码
     */
    private Byte code;
    /**
     * 描述
     */
    private String remark;

    MemberCertificatesTypeEnum(Byte code, String remark) {
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
