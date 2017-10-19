package com.ph.shopping.facade.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述： 商品VO
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 上午10:29:43
 *
 * @Copyright by 杨颜光
 */
public class ProductDTO implements Serializable{
   
	private static final long serialVersionUID = -182945905826655815L;

	/** 商品id */
    private Long id;

    /** 商品名称 */
    private String productName;

    /** 商品总数量 */
    private Integer productCount;

    /** 商品描述 */
    private String description;

    /** 商品类别id */
    private Long productClassifyId;

    /** 上架下架状态 0 上架 1未上架 */
    private Byte saleNoSaleStatus;

    /** 审核状态 0待审核 1未通过 2通过*/
    private Byte auditState;

    /** 删除标记 0未删除 1已删除 */
    private Byte isDelete;

    /** 商品来源 1 平台 2 代理商 */
    private Byte productForm;

    /** 条形码 */
    private String barCode;

    /** 供应商id */
    private Long supplierId;

    /** 类型区分1.全国，2本地 */
    private Byte productType;
    
    /** 创建时间*/
    private  Date createTime;
    
    /** 修改时间*/
    private Date  updateTime;
    
    /** 创建人*/
    private  Long createrId;
    
    /** 修改人*/
    private  Long  updaterId;
    
    /** 商品销量（为供应链和商城的总销量） */
    private  Integer  commoditySales;
    
    
    /**本地供应商id*/
    private List<Long> supplierIds;
    
    /**商城按上架时间(修改时间)、销量、价格排序查询条件*/
    private Byte  queryItem;
    
    /**商城按最低价序查询条件*/
    private BigDecimal  minPrice;
    
    /**商城按最高价查询条件*/
    private BigDecimal  maxPrice;
    
    /**商城首页图片自定义地址*/
    private  String  indexImageUrl;
    
    private BigDecimal  retailPrice;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProductClassifyId() {
		return productClassifyId;
	}

	public void setProductClassifyId(Long productClassifyId) {
		this.productClassifyId = productClassifyId;
	}

	public Byte getSaleNoSaleStatus() {
		return saleNoSaleStatus;
	}

	public void setSaleNoSaleStatus(Byte saleNoSaleStatus) {
		this.saleNoSaleStatus = saleNoSaleStatus;
	}

	public Byte getAuditState() {
		return auditState;
	}

	public void setAuditState(Byte auditState) {
		this.auditState = auditState;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
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
		this.barCode = barCode;
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

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Integer getCommoditySales() {
		return commoditySales;
	}

	public void setCommoditySales(Integer commoditySales) {
		this.commoditySales = commoditySales;
	}

	public List<Long> getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(List<Long> supplierIds) {
		this.supplierIds = supplierIds;
	}

	public Byte getQueryItem() {
		return queryItem;
	}

	public void setQueryItem(Byte queryItem) {
		this.queryItem = queryItem;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getIndexImageUrl() {
		return indexImageUrl;
	}

	public void setIndexImageUrl(String indexImageUrl) {
		this.indexImageUrl = indexImageUrl;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	
	

	
	
	
	
	
	
	
	
	
}