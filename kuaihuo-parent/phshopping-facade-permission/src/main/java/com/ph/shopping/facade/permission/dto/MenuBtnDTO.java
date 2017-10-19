package com.ph.shopping.facade.permission.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-permission
 * @描述： 菜单按钮关系DTO
 * @作者： Mr.Shu
 * @创建时间：2017-03-14
 * @Copyright @2017 by Mr.Shu
 */
public class MenuBtnDTO implements Serializable {

    private static final long serialVersionUID = -5732747489277713919L;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 按钮id
     */
    private List<Long> btnIds;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建人
     */
    private Long createrId;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Long> getBtnIds() {
        return btnIds;
    }

    public void setBtnIds(List<Long> btnIds) {
        this.btnIds = btnIds;
    }

    public Long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
