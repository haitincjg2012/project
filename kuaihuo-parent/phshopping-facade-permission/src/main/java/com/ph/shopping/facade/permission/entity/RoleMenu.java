package com.ph.shopping.facade.permission.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 * @描述： 角色菜单实体
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_role_menu")
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 8355710622359264368L;

    /**
     * 角色id
     */
    @Column(name = "roleId")
    private Long roleId;

    /**
     * 菜单id
     */
    @Column(name = "menuId")
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}
