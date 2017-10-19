package com.ph.shopping.facade.member.vo;

import java.io.Serializable;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 银行卡关联开户银行表
 * @作者： 熊克文
 * @创建时间： 2017/5/23
 * @Copyright by xkw
 */
public class BankCodenameDataVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4077321821762619569L;
    /**
     * id
     */
    private Long id;
    /**
     * 银行卡名称
     */
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
