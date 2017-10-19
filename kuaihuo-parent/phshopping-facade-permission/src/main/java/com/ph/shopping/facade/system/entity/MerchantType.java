package com.ph.shopping.facade.system.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户类型表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
@Table(name = "ph_merchant_type")
public class MerchantType extends BaseEntity {

    private static final long serialVersionUID = 5819906580727069947L;

    /**
     * 商户类型名称
     */
    @Column(name = "merchantTypeName")
    private String merchantTypeName;

    /**
     * 父级id(默认为0)
     */
    @Column(name = "parentId")
    private Long parentId;

    /**
     * 创建人
     */
    @Column(name = "createrId")
    private Long createrId;

    /**
     * 修改人id
     */
    @Column(name = "updaterId")
    private Long updaterId;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

    /**
     * 是否删除(0.删除，1未删除)默认为1
     */
    @Column(name = "isDelete")
    private Byte isDelete;

    /**
     * 分类级别 1 一级分类 2 二级分类
     */
    @Column(name = "merchanTypeLevel")
    private Byte merchanTypeLevel;

    /**
     * 描述
     */
    @Column(name = "`desc`")
    private String desc;

    /**
     * 图标(图标的地址)
     */
    @Column(name = "icon")
    private String icon;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMerchantTypeName() {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName) {
        this.merchantTypeName = merchantTypeName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getMerchanTypeLevel() {
        return merchanTypeLevel;
    }

    public void setMerchanTypeLevel(Byte merchanTypeLevel) {
        this.merchanTypeLevel = merchanTypeLevel;
    }
}