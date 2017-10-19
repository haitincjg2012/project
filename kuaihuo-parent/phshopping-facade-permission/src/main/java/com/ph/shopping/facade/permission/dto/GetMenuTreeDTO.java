package com.ph.shopping.facade.permission.dto;

import com.ph.shopping.facade.permission.vo.MenuTreeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @项目：phsopping-facade-permission
 * @描述：获取菜单按钮列表
 * @作者： Mr.Shu
 * @创建时间：2017-03-15
 * @Copyright @2017 by Mr.Shu
 */
public class GetMenuTreeDTO implements Serializable {

    private static final long serialVersionUID = -5350629229232288288L;

    //角色ID
    private Long roleId;

    /**
     * 获取菜单树列表
     */
    private List<MenuTreeVO> menuTreeVOS;

    public List<MenuTreeVO> getMenuTreeVOS() {
        return menuTreeVOS;
    }

    public void setMenuTreeVOS(List<MenuTreeVO> menuTreeVOS) {
        this.menuTreeVOS = menuTreeVOS;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
