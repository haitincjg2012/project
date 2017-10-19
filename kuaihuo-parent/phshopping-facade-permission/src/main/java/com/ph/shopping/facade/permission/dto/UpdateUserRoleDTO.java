package com.ph.shopping.facade.permission.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @项目：phshopping-facade-permission
 * @描述：
 * @作者： Mr.zheng
 * @创建时间：2017-03-16
 * @Copyright @2017 by Mr.zheng
 */
public class UpdateUserRoleDTO implements Serializable {

    private static final long serialVersionUID = 3307667487567473618L;

    private Long userId;

    private String telPhone;

    private List<Long> roleIds;

    private Long createrId;

    private Long updaterId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}
