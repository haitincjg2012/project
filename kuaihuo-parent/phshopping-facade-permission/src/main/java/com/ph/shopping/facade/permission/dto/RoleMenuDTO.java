package com.ph.shopping.facade.permission.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @项目：phshopping-facade-permission
 * @描述：
 * @作者： Mr.Shu
 * @创建时间：2017-05-17
 * @Copyright @2017 by Mr.Shu
 */
public class RoleMenuDTO implements Serializable {

    private static final long serialVersionUID = -5732747489277713919L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private List<Long> menuIds;
    /**
     * 创建人
     */
    private Long createrId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }
}
