package com.ph.shopping.facade.system.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户类型表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantTypeAppVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2295571366533551084L;

    /**
     * id
     */
    private Long id;

    /**
     * 商户类型名称
     */
    private String merchantTypeName;

    /**
     * 商户总人数
     */
    private Long merchantCount;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 分类级别 1 一级分类 2 二级分类
     */
    private Byte merchanTypeLevel;
    /**
     * 描述
     */
    private String desc;
    /**
     * 图标(图标的地址)
     */
    private String icon;
    //类别子集
    private List<MerchantTypeAppVO> children;

    public Long getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(Long merchantCount) {
        this.merchantCount = merchantCount;
    }

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

    public List<MerchantTypeAppVO> getChildren() {
        if (children == null) {
            this.setChildren(new ArrayList<>());
        }
        return children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantTypeName() {
        return merchantTypeName;
    }

    public void setMerchantTypeName(String merchantTypeName) {
        this.merchantTypeName = merchantTypeName;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Byte getMerchanTypeLevel() {
        return merchanTypeLevel;
    }

    public void setMerchanTypeLevel(Byte merchanTypeLevel) {
        this.merchanTypeLevel = merchanTypeLevel;
    }

    protected void setChildren(List<MerchantTypeAppVO> children) {
        this.children = children;
    }

}