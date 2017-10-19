package com.ph.shopping.facade.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 商品信息VO 
 * @author 何文浪
 *
 */
public class ProductVO implements Serializable{
    
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
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /** 修改时间*/
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date  updateTime;
    
    /** 创建人*/
    private  Long createrId;
    
    /** 修改人*/
    private  Long  updaterId;
    
    /** 商品销量（为供应链和商城的总销量） */
    private  Integer  commoditySales;
    
//  private List<ProductImageVO> productImageVoList;
  
  /** 分类名称 */
  private  String  productClassifyName;
  
  /**供应商名称  */
  private  String supplierName;
  
  /** 冻结状态*/
  private  Byte isFrozen;
  
  /** 规格零售价的最低价格*/
  private BigDecimal retailPrice;
  
  /**商品图片路径*/
  private  String url;
  
  private  Integer  imageSort;
  
  /**商城首页图片自定义地址*/
  private  String  indexImageUrl;
  
  /**商城首页图片自定义地址*/
  private   Long    classifyIdForIndex;

	/**商城首页图片0:不是大图, 1是大图*/
  private  Byte isBigImg;

	public Byte getIsBigImg() {
		return isBigImg;
	}

	public void setIsBigImg(Byte isBigImg) {
		this.isBigImg = isBigImg;
	}

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

	public String getProductClassifyName() {
		return productClassifyName;
	}

	public void setProductClassifyName(String productClassifyName) {
		this.productClassifyName = productClassifyName;
	}

	public Integer getCommoditySales() {
		return commoditySales;
	}

	public void setCommoditySales(Integer commoditySales) {
		this.commoditySales = commoditySales;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Byte getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(Byte isFrozen) {
		this.isFrozen = isFrozen;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getImageSort() {
		return imageSort;
	}

	public void setImageSort(Integer imageSort) {
		this.imageSort = imageSort;
	}

	public String getIndexImageUrl() {
		return indexImageUrl;
	}

	public void setIndexImageUrl(String indexImageUrl) {
		this.indexImageUrl = indexImageUrl;
	}

	public Long getClassifyIdForIndex() {
		return classifyIdForIndex;
	}

	public void setClassifyIdForIndex(Long classifyIdForIndex) {
		this.classifyIdForIndex = classifyIdForIndex;
	}

	
}