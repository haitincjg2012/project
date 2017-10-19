package com.ph.shopping.facade.permission.dto;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-permission
 * @描述：
 * @作者： Mr.zheng
 * @创建时间：2017-04-10
 * @Copyright @2017 by Mr.zheng
 */
public class UpdatePassDTO implements Serializable {

    private static final long serialVersionUID = -3060582282802990879L;

    /**
     * 原始密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 用户id
     */
    private Long id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
