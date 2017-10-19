package com.ph.shopping.facade.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性实体
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年4月24日 下午5:52:10
 *
 * @Copyright by 杨颜光
 */
@Table(name="ph_product_property")
public class ProductProperty extends BaseEntity   implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6399437107240051555L;


    /** 属性名称 */
	@Column(name="propertyName")
	@NotBlank(message="[属性名称]不可为空")
   	@Length(max=100,message="[属性名称]最大长度为100个字符")
    private String propertyName;

    /** 商品分类id */
	@Column(name="classifyId")
    private Long classifyId;

	 /** 排序 */
	@Column(name="sort")
	private Integer sort;
	
	 /** 删除标记 0是未删除，1是已删除。*/
	@Column(name="isDelete")
	private Byte isDelete;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Long getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}
	
  
	

	
	

    
	
}