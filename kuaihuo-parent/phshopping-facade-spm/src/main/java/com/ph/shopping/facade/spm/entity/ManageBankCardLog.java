package com.ph.shopping.facade.spm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 * @描述：银行卡日志实体
 * @作者： ShuHao
 * @创建时间：2017-03-28
 * @Copyright @2017 by ShuHao
 */
@Table(name = "ph_user_bank_card_log")
public class ManageBankCardLog implements Serializable {
    private static final long serialVersionUID = -9056436515111782366L;
    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 创建人
     */
    @Column(name = "createrId")
    private Long createrId;

    /**
     * 用户Id
     */
    @Column(name = "userId")
    private Long userId;

    /**
     * 银行卡号
     */
    @Column(name = "bankCardNo")
    private String bankCardNo;

    /**
     * 银行所绑定的手机号
     */
    @Column(name = "telPhone")
    private String telPhone;

    /**
     * 开户银行名字
     */
    @Column(name = "bankName")
    private String bankName;

    /**
     * 开户人姓名
     */
    @Column(name = "ownerName")
    private String ownerName;

    /**
     * 操作类型
     */
    @Column(name = "useType")
    private Byte useType;

    /**
     * 身份证号
     */
    @Column(name = "idCardNo")
    private String idCardNo;

    /**
     * 绑定表的主键id
     */
    @Column(name = "bindCardId")
    private Long bindCardId;

    /**
     * 创建ip
     */
    @Column(name = "createIp")
    private String createIp;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Byte getUseType() {
		return useType;
	}

	public void setUseType(Byte useType) {
		this.useType = useType;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Long getBindCardId() {
		return bindCardId;
	}

	public void setBindCardId(Long bindCardId) {
		this.bindCardId = bindCardId;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

}
