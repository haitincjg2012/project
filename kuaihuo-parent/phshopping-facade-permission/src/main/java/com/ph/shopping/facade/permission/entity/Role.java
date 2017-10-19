package com.ph.shopping.facade.permission.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述： 角色实体类
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-05-12
 *
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 7375175379375246073L;

    /**
     * 角色名称
     */
    @Column(name = "roleName")
    private String roleName;


    /**
     * 角色编号
     */
    @Column(name = "roleCode")
    private Byte roleCode;


    /**
     * 角色状态 1 ：启用 2：停用
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 角色描述
     */
    @Column(name = "description")
    private String description;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Byte getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Byte roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
