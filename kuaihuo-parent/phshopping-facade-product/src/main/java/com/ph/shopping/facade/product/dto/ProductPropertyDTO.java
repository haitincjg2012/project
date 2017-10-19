package com.ph.shopping.facade.product.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：属性DTO
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月15日 上午11:54:24
 *
 * @Copyright by 杨颜光
 */
public class ProductPropertyDTO  implements Serializable{
  
	private static final long serialVersionUID = 6394110613597664792L;

	/** 主键id */
    private Long id;

    /** 属性名称 */
    private String propertyName;

    /** 商品分类id */
    private Long classifyId;
    
    private String classifyName;//商品分类名称
    
    /** 排序 */
    private Integer sort;
    
    /** 创建人*/
    private Long createrId;
    
    /** 新增时间时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    
    /** 修改人*/
    private Long updaterId;
    
    /** 修改时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;
    
    /** 删除标记 0 未删除 1 已删除*/
    private  Byte isDelete;
    
    /** 与分类联合查询使用字段（分类停用启用字段）*/ 
    private  Byte  status;
    
//    private List<PhProductPropertyValDTO> phProductPropertyValVoList;//商品属性值
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
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

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	
   
}