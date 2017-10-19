package com.ph.shopping.facade.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品属性值实体
 *
 * 			@作者： 杨颜光
 *
 * @创建时间：2017年4月24日 下午5:52:42
 *
 * @Copyright by yyg
 */
@Table(name = "ph_product_property_val")
public class ProductPropertyVal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6494830790906664318L;

	/** 主键id */
	@Id
	private Long id;

	/** 商品属性id */
	@Column(name = "propertyId")
	private Long propertyId;

	/** 商品属性值 */
	@Column(name = "content")
	private String content;

	/** 商品id */
	@Column(name = "productId")
	private Long productId;

	/** 创建人 */
	@Column(name = "createrId")
	private Long createrId;

	/** 创建时间 */
	@Column(name = "createTime")
	private Date createTime;

	public Long getPropertyId() {
		return propertyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}




	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	
}