package com.ph.shopping.facade.permission.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述： 用户角色实体
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-05-12
 *
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_user_role")
public class UserRole extends BaseEntity{

    private static final long serialVersionUID = -6782858999602872500L;
    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Long roleId;

    /**
     * 用户id
     */
    @Column(name = "userId")
    private Long userId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
