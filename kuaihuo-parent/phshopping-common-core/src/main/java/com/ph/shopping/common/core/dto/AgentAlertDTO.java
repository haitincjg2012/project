package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * @author xwolf
 * @since 1.8
 **/
public class AgentAlertDTO extends BaseValidate {

    @NotNull(message = "手机号码不能为空")
    private String phone	;//	用户手机号

    @NotNull(message = "代理属地新商户号不能为空")
    private String inviteMembers;//	代理属地新商户号

    @NotNull(message = "客服号码不能为空")
    private String kfPhone;//	客服号码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
