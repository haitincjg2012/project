package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * @项目：phshopping-facade-permission
 * @描述：绑定银行卡实体
 * @作者： ShuHao
 * @创建时间：2017-03-28
 * @Copyright @2017 by ShuHao
 */
@Table(name = "ph_user_bank_card_bind")
public class ManageBankCardBind extends BaseEntity {
    private static final long serialVersionUID = -9056436515111782366L;

    /**
     * 银行卡信息Id
     */
    @Column(name = "bankCardInfoId")
    private Long bankCardInfoId;

    /**
     * 用户Id
     */
    @Column(name = "userId")
    private Long userId;

    /**
     * 绑定状态   1 已绑定 2 未绑定
     */
    @Column(name = "bindStatus")
    private Integer bindStatus;

    public Long getBankCardInfoId() {
        return bankCardInfoId;
    }

    public void setBankCardInfoId(Long bankCardInfoId) {
        this.bankCardInfoId = bankCardInfoId;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
