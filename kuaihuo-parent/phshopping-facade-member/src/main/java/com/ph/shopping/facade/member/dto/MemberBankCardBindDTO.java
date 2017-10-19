package com.ph.shopping.facade.member.dto;


/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： description
 * @作者： 熊克文
 * @创建时间： 2017/6/16
 * @Copyright by xkw
 */
public class MemberBankCardBindDTO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 绑定状态
     */
    private Byte bindStatus;

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
