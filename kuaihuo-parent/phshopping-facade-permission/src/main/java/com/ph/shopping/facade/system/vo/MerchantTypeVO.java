package com.ph.shopping.facade.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户类型表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantTypeVO implements Serializable, Comparable<MerchantTypeVO> {
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
     * 描述
     */
    private String desc;


    /**
     * 图标(图标的地址)
     */
    private String icon;

    /**
     * 父级id(默认为0)
     */
    private Long parentId;

    /**
     * 创建人
     */
    private Long createrId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 修改人id
     */
    private Long updaterId;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 商户总人数
     */
    private Long merchantCount;

    /**
     * 是否启用(0.启用，1未启用)默认为1
     */
    private Byte isDelete;

    /**
     * 分类级别 1 一级分类 2 二级分类
     */
    private Byte merchanTypeLevel;

    //类别子集
    private List<MerchantTypeVO> children;

    @Transient
    private ArrayList<Long> sortList;

    public Long getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(Long merchantCount) {
        this.merchantCount = merchantCount;
    }

    public MerchantTypeVO() {
    }

    public MerchantTypeVO(Long id, Long merchantCount, String merchantTypeName, String desc, String icon, Long parentId, Long createrId,
                          Date createTime, Long updaterId, Date updateTime, Long sort, Byte isDelete, Byte merchanTypeLevel, ArrayList<Long> sortList) {
        this.id = id;
        this.merchantCount = merchantCount;
        this.merchantTypeName = merchantTypeName;
        this.parentId = parentId;
        this.createrId = createrId;
        this.createTime = createTime;
        this.updaterId = updaterId;
        this.updateTime = updateTime;
        this.sort = sort;
        this.isDelete = isDelete;
        this.merchanTypeLevel = merchanTypeLevel;
        this.sortList = sortList;
        this.desc = desc;
        this.icon = icon;
    }

    public List<MerchantTypeVO> getChildren() {
        if (children == null) {
            this.setChildren(new ArrayList<>());
        }
        return children;
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

    public void setChildren(List<MerchantTypeVO> chidren) {
        this.children = chidren;
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<Long> getSortList() {
        return sortList;
    }

    public void setSortList(ArrayList<Long> sortList) {
        this.sortList = sortList;
    }

    @Override
    public int compareTo(MerchantTypeVO o) {
        int thisSize = this.getSortList().size();
        int otherSize = o.getSortList().size();
        int shortSize = thisSize > otherSize ? otherSize : thisSize;

        for (int i = 0; i < shortSize; i++) {
            Long compareValue = this.getSortList().get(i) - o.getSortList().get(i);
            if (compareValue != 0) {
                return compareValue.intValue();
            }
        }

        return otherSize == thisSize ? 0 : thisSize - otherSize;
    }
}