package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * 商户/会员推广奖金通知
 * @author xwolf
 * @since 1.8
 **/
public class SpreadDTO extends BaseValidate {

    @NotNull(message = "手机号不能为空")
    private String phone	;//	用户手机号

    @NotNull(message = "邀请的商户/会员角色不能为空")
    private String role	;//	邀请的商户/会员角色

    @NotNull(message = "邀请的商户/会员消费金额不能为空")
    private String money	;//	邀请的商户/会员消费金额

    @NotNull(message = "商户/会员账号不能为空")
    private String inviteMembers	;//	邀请的商户/会员账号

    @NotNull(message = "客服号码不能为空")
    private String kfPhone	;//	客服号码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInviteMembers() {
        return inviteMembers;
    }

    public void setInviteMembers(String inviteMembers) {
        this.inviteMembers = inviteMembers;
    }

    public String getKfPhone() {
        return kfPhone;
    }

    public void setKfPhone(String kfPhone) {
        this.kfPhone = kfPhone;
    }
}
