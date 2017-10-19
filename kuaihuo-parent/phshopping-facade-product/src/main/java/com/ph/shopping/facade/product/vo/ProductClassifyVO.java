package com.ph.shopping.facade.product.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * ProductClassifyVO 商品分类VO
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-15 11:45:07
 */
public class ProductClassifyVO implements Serializable, Comparable<ProductClassifyVO>{
    /**
	 * 
	 */
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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;

	/** 修改时间 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/** 创建人 */
	private Long createrId;

	/** 修改人 */
	private Long updaterId;

	/** 该类型下商品总数(扩展) */
    private Integer productCount;

	/** 树形表格排序 */
	private ArrayList<Integer> sortList;

	/** 是否选中 用于页面回显 0否 1是 */
	private Integer isSelect;

	public ProductClassifyVO(){}

	public ProductClassifyVO(Long id, String classifyName, Byte classifyLevel,
							 String description, String url, Long parentId, Byte status,
							 Byte isDelete, Integer sort, Date createTime, Date updateTime,
							 Long createrId, Long updaterId, Integer productCount, ArrayList<Integer> sortList) {
		this.id = id;
		this.classifyName = classifyName;
		this.classifyLevel = classifyLevel;
		this.description = description;
		this.url = url;
		this.parentId = parentId;
		this.status = status;
		this.isDelete = isDelete;
		this.sort = sort;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createrId = createrId;
		this.updaterId = updaterId;
		this.productCount = productCount;
		this.sortList = sortList;
	}

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

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}


	public ArrayList<Integer> getSortList() {
		return sortList;
	}

	public void setSortList(ArrayList<Integer> sortList) {
		this.sortList = sortList;
	}

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

	@Override
	public int compareTo(ProductClassifyVO o) {
		int thisSize = this.getSortList().size();
		int otherSize = o.getSortList().size();
		int shortSize = thisSize > otherSize ? otherSize : thisSize;

		for (int i = 0; i < shortSize; i++) {
			Integer compareValue = this.getSortList().get(i) - o.getSortList().get(i);
			if (compareValue != 0) {
				return compareValue.intValue();
			}
		}

		return otherSize == thisSize ? 0 : thisSize - otherSize;
	}
}