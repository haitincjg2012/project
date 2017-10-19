package com.ph.shopping.facade.product.vo;

import java.io.Serializable;
import java.util.Date;

public class ProductSnapshotVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1182348923286229095L;

	/** 主键id */
    private Long id;

    /** 商品id */
    private Long productId;

    /** 商品名称 */
    private String productName;

    /** 商品总数 */
    private Integer productCount;

    /** 商品类别id */
    private Long productClassifyId;

    /** 商品来源 1 平台 2 代理商 */
    private Byte productForm;

    /** 条形码 */
    private String barCode;

    /** 供应商id */
    private Long supplierId;

    /** 类型区分1.全国，2本地 */
    private Byte productType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 创建人 */
    private Long createrId;

    /** 商品销量 */
    private Integer commoditySales;

    /** 修改人 */
    private Long updaterId;

    /** 商品描述 */
    private String description;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getProductClassifyId() {
        return productClassifyId;
    }

    public void setProductClassifyId(Long productClassifyId) {
        this.productClassifyId = productClassifyId;
    }

    public Byte getProductForm() {
        return productForm;
    }

    public void setProductForm(Byte productForm) {
        this.productForm = productForm;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
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

    public Integer getCommoditySales() {
        return commoditySales;
    }

    public void setCommoditySales(Integer commoditySales) {
        this.commoditySales = commoditySales;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}