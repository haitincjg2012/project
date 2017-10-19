package com.ph.shopping.facade.permission.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-05-12
 *
 * @Copyright @2017 by Mr.Shu
 */
public class UserVO implements Serializable {

    private static final long serialVersionUID = -5195280967436667731L;


    /**
     * 用户id
     */
    private Long id;


    /**
     * 用户名
     */
    private String userName;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 代理商名称
     */
    private String agentName;

    /**
     * 供应商名
     */
    private String supplierName;

    /**
     * 代理商状态
     */
    private Byte agentStatus;

    /**
     * 商户状态
     */
    private Byte merchantStatus;

    /**
     * 供应商状态
     */
    private Byte supplierStatus;

    /**
     * 代理商审核
     */
    private Byte agentPass;

    /**
     * 商户审核
     */
    private Byte merchantPass;

    /**
     * 供应商审核
     */
    private Byte supplierPass;

    /**
     * 状态 1 启用 2 禁用
     */
    private Byte isable;

    /**
     * 手机号
     */
    private String telphone;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsable() {
        return isable;
    }

    public void setIsable(Byte isable) {
        this.isable = isable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Byte getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(Byte agentStatus) {
        this.agentStatus = agentStatus;
    }

    public Byte getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(Byte merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public Byte getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(Byte supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public Byte getAgentPass() {
        return agentPass;
    }

    public void setAgentPass(Byte agentPass) {
        this.agentPass = agentPass;
    }

    public Byte getMerchantPass() {
        return merchantPass;
    }

    public void setMerchantPass(Byte merchantPass) {
        this.merchantPass = merchantPass;
    }

    public Byte getSupplierPass() {
        return supplierPass;
    }

    public void setSupplierPass(Byte supplierPass) {
        this.supplierPass = supplierPass;
    }
}
