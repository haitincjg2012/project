package com.ph.shopping.common.core.dto;

import com.ph.shopping.common.core.base.BaseValidate;

import javax.validation.constraints.NotNull;

/**
 * 推送
 * @author xwolf
 * @since 1.8
 **/
public class PushDTO extends BaseValidate {

    @NotNull(message = "推送内容不能为空")
    private String content	;//	推送内容

    @NotNull(message = "推送的用户信息不能为空")
    private String userId	;//	推送的用户信息

    @NotNull(message = "标题不能为空")
    private String title	;//	推送的标题信息

    @NotNull(message = "推送的类型不能为空")
    private String codeType	;//	推送的类型  fr0001快火会员  fr0002 快火批发商


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
