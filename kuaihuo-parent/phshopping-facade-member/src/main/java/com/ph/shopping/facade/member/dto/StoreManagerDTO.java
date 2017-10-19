package com.ph.shopping.facade.member.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import java.io.Serializable;

/**
 * 店面代理经理
 * Created by wudi on 2017/9/25.
 */
public class StoreManagerDTO extends BaseValidate implements Serializable {

    /**
     * 当前用户的id

     */
     private Long id;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;



    /**
     * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -4747734633757244024L;
    /**
     * 手机号
     */
    private String telPhone;
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 状态  0在职 1解聘
     */
    private Byte status;
    /**
     * 认证 0审核中 1已审核 2审核失败
     */
    private Byte certification;
    /**
     * 商户的公司名
     */
    private String merchantName;
    /**
     * 代理的市级地址
     */
    private String cityName;

    /**
     * 登录用户的手机号
     */
    private String rolePhone;
    /**
     * 登录者的级别，最高级别为1

     */
    private Byte roleCode;

    public Byte getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Byte roleCode) {
        this.roleCode = roleCode;
    }



    public String getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(String isFrozen) {
        this.isFrozen = isFrozen;
    }

    /**
     * 冻结状态

     */
    private String isFrozen;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 登录用户的角色

     */
    private String role;

    public String getRolePhone() {
        return rolePhone;
    }

    public void setRolePhone(String rolePhone) {
        this.rolePhone = rolePhone;
    }
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getCertification() {
        return certification;
    }

    public void setCertification(Byte certification) {
        this.certification = certification;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
