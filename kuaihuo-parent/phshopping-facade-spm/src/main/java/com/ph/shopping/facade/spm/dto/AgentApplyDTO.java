package com.ph.shopping.facade.spm.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 代理申请DTO
 * @作者： 熊克文
 * @创建时间： 2017/6/8
 * @Copyright by xkw
 */
public class AgentApplyDTO extends AgentDTO {

    //验证码
    @NotBlank(message = "验证码不能为空")
    private String yzm;

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }
}
