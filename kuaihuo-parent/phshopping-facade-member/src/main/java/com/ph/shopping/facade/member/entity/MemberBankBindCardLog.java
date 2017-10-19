package com.ph.shopping.facade.member.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员绑定银行卡流水表
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
@Table(name = "ph_member_bank_card_log")
public class MemberBankBindCardLog implements Serializable {

    private static final long serialVersionUID = 1383074938190841027L;
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 用户id
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
    @Column(name = "bindPhone")
    private String bindPhone;
    /**
     * 开户银行卡基础信息id
     */
    @Column(name = "bankCodenameDataId")
    private Long bankCodenameDataId;
    /**
     * 开户人姓名
     */
    @Column(name = "ownerName")
    private String ownerName;
    /**
     * 身份证号
     */
    @Column(name = "idCardNo")
    private String idCardNo;
    /**
     * 绑定id
     */
    @Column(name = "bindCardId")
    private Long bindCardId;
    /**
     * 操作类型 1绑定，2解绑
     */
    @Column(name = "operationType")
    private Byte operationType;
    /**
     * 创建ip
     */
    @Column(name = "createIp")
    private String createIp;
    /**
     * 创建人
     */
    @Column(name = "createrId")
    private Long createrId;
    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }

    public Long getBankCodenameDataId() {
        return bankCodenameDataId;
    }

    public void setBankCodenameDataId(Long bankCodenameDataId) {
        this.bankCodenameDataId = bankCodenameDataId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Byte getOperationType() {
        return operationType;
    }

    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
