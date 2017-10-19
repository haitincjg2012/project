package com.ph.shopping.facade.member.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 会员银行卡绑定关系表
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
@Table(name = "ph_member_bank_card_bind")
public class MemberBankCardBind extends BaseEntity {

    private static final long serialVersionUID = -62215255773636515L;
    /**
     * 银行卡信息id
     */
    @Column(name = "bankCardInfoId")
    private Long bankCardInfoId;
    /**
     * 用户id
     */
    @Column(name = "userId")
    private Long userId;
    /**
     * 绑定状态
     */
    @Column(name = "bindStatus")
    private Byte bindStatus;

    public Long getBankCardInfoId() {
        return bankCardInfoId;
    }

    public void setBankCardInfoId(Long bankCardInfoId) {
        this.bankCardInfoId = bankCardInfoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Byte bindStatus) {
        this.bindStatus = bindStatus;
    }
}
