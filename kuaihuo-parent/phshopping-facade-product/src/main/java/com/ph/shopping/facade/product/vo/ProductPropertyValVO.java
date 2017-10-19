package com.ph.shopping.facade.product.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品属性值DTO
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午3:20:49
 *
 * @Copyright by 杨颜光
 */
public class ProductPropertyValVO  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5730014448529071938L;
	
	/** 主键id */
	private Long id;

	/** 商品属性id */
	private Long propertyId;

	/** 商品属性值 */
	private String content;

	/** 商品id */
	private Long productId;

	/** 创建人 */
	private Long createrId;

	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	
}