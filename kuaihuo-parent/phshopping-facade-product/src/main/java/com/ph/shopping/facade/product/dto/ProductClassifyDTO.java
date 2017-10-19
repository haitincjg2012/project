package com.ph.shopping.facade.product.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ProductClassifyDTO 商品分类查询DTO
 *
 * @version 2.1
 * @author: 李超
 * @date: 2017-05-15 11:31:30
 */
public class ProductClassifyDTO implements Serializable{

	private static final long serialVersionUID = -7532211027831490720L;

	/** 商品分类id */
    private Long id;

    /** 商品分类名称 */
    private String classifyName;

    /** 分类级别 1 一级分类 2 二级分类 3 三级分类 */
    private Byte classifyLevel;

	/** 描述 */
	private String description;

	/** 分类图标地址 */
	private String url;

    /** 父id */
    private Long parentId;

    /** 是否启用 0 未启用，1已经启用 */
    private Byte status;

    /** 是否删除 0 未删除，1 已删除 */
    private Byte isDelete;

	/** 排序 */
	private Integer sort;

    /** 创建时间 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /** 修改时间 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

	/** 创建人 */
	private Long createrId;

	/** 修改人 */
	private Long updaterId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
}