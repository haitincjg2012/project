package com.ph.shopping.facade.permission.entity;

import com.ph.shopping.common.core.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述： 菜单实体表
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-05-12
 *
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_permission_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 3409139139798172232L;

    /**
     * 菜单名称
     */
    @Column(name = "menuName")
    private String menuName;

    /**
     * 菜单地址
     */
    @Column(name = "menuUrl")
    private String menuUrl;

    /**
     * 菜单父id
     */
    @Column(name = "parentID")
    private Long parentID;

    /**
     * 菜单顺序
     */
    @Column(name = "sequence")
    private Integer sequence;

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    private String icon;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
