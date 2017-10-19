package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: CertificatesAuthDTO
 * @Description:身份证认证参数
 * @author: 熊克文
 * @date: 2017/5/23
 * @Copyright: 2017
 */
public class CertificatesAuthDTO extends BaseValidate implements Serializable {

    private static final long serialVersionUID = 2249806983130505215L;

    /**
     * 证件名称
     */
    @NotNull(message = "证件名称不能为空")
    private String certificatesName;
    /**
     * 证件号码
     */
    @NotNull(message = "证件号码不能为空")
    private String certificatesCode;
    /**
     * 证件类型枚举编码
     */
    @NotNull(message = "证件类型编码不能为空")
    private Byte certificatesType;
    /**
     * 用户ID
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 登陆人id
     */
    @NotNull(message = "当前登陆人id不能为空")
    private Long loginUserId;

    /**
     * 预留认证字段1 可能另外的认证需要的字段
     */
    private String remark1;
    /**
     * 预留认证字段2
     */
    private String remark2;

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCertificatesName() {
        return certificatesName;
    }

    public void setCertificatesName(String certificatesName) {
        this.certificatesName = certificatesName;
    }

    public String getCertificatesCode() {
        return certificatesCode;
    }

    public void setCertificatesCode(String certificatesCode) {
        this.certificatesCode = certificatesCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getCertificatesType() {
        return certificatesType;
    }

    public void setCertificatesType(Byte certificatesType) {
        this.certificatesType = certificatesType;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
}
