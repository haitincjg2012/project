package com.ph.shopping.facade.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * ProductClassify 商品分类实体类
 *
 * @version: 2.1更新
 * @author: 李超
 * @date: 2017-05-12 10:45:37
 */
@Table(name="ph_product_classify")
public class ProductClassify extends BaseEntity  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3019628828523605144L;


    /** 商品分类名称 */
	@Column(name="classifyName")
	@NotBlank(message="名称不能为空")
   	@Length(max=100,message="名称最大长度为100个字符")
    private String classifyName;

    /** 分类级别 1 一级分类 2 二级分类 3 三级分类 */
	@Column(name="classifyLevel")
    private Byte classifyLevel;

    /** 父id */
	@Column(name="parentId")
    private Long parentId;

    /** 是否启用 0 未启用，1 已经启用*/
	@Column(name="status")
    private Byte status;

    /** 是否删除 0:未删除 1：已删除 */
	@Column(name="isDelete")
    private Byte isDelete;
	
	/** 描述 */
	@Column(name="description")
	private String description;
	
	/** 排序 */
    @NotNull(message="排序不能为空")
    @Min(value=1, message="排序不能为空")
    @Column(name="sort")
	private Integer sort;

    /** 图片地址 */
    @Column(name="url")
	private String url;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Byte getClassifyLevel() {
        return classifyLevel;
    }

    public void setClassifyLevel(Byte classifyLevel) {
        this.classifyLevel = classifyLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}