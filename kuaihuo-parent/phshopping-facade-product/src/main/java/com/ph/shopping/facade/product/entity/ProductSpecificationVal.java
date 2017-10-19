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
 * @描述：商品规格类型值实体类
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月12日 上午11:28:37
 *
 * @Copyright by杨颜光
 */
@Table(name="ph_product_specification_val")
public class ProductSpecificationVal implements Serializable {
	
	private static final long serialVersionUID = 545433759410709860L;

	/** 主键 */
	@Id
    private Long id;

    /** 规格名称id */
	@Column(name="specificationId")
    private Long specificationId;

    /** 规格值 */
	@Column(name="specificationValue")
    private String specificationValue;

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

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationValue() {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue) {
        this.specificationValue = specificationValue == null ? null : specificationValue.trim();
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