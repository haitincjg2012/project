package com.ph.shopping.facade.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @项目：phshopping-facade-product
 *
 * @描述：商品规格类型实体类
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月12日 上午11:28:37
 *
 * @Copyright by杨颜光
 */
@Table(name="ph_product_specification")
public class ProductSpecification implements Serializable {
	
	private static final long serialVersionUID = 9077005285972421622L;

	/** 主键 */
	@Id
    private Long id;

    /** 商品id */
	@Column(name="productId")
    private Long productId;

    /** 规格名称 */
	@Column(name="specificationName")
    private String specificationName;

    /** 创建人 */
	@Column(name="createrId")
    private Long createrId;

    /** 创建时间 */
	@Column(name="createTime")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName == null ? null : specificationName.trim();
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