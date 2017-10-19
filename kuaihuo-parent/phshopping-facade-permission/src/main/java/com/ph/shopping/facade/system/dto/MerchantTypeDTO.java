package com.ph.shopping.facade.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @项目：phshopping-facade-spm
 * @描述：商户类型表
 * @作者 何文浪
 * @时间：2017-5-12
 * @version: 2.1
 */
public class MerchantTypeDTO extends BaseValidate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2295571366533551084L;

	/** id */
    private Long id;

    /** 商户类型名称 */
    @NotBlank(message = "商户类型名称不能为空")
    private String merchantTypeName;

    /** 父级id(默认为0) */
    private Long parentId;

    /** 创建人 */
    private Long createrId;

    /** 创建时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createrTime;

    /** 修改人id */
    private Long updaterId;

    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updaterTime;

    /** 排序 */
    private Long sort;

    /**
     * 是否启用(0.启用，1未启用)默认为1
     */
    private Byte isDelete;

    /** 分类级别 1 一级分类 2 二级分类 */
    @NotNull(message = "商户类别级别不能为空")
    private Byte merchanTypeLevel;

    /**
     * 描述
     */
    private String desc;

    /**
     * 图标(图标的地址)
     */
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

    public Date getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdaterTime() {
        return updaterTime;
    }

    public void setUpdaterTime(Date updaterTime) {
        this.updaterTime = updaterTime;
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