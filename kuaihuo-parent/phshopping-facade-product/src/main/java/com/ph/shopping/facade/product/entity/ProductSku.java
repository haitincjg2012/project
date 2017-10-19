package com.ph.shopping.facade.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 
 * @项目：phshopping-facade-product
 *
 * @描述：商品sku实体类
 *
 * @作者：杨颜光
 *
 * @创建时间：2017年5月12日 上午11:27:06
 *
 * @Copyright by杨颜光
 */
@Table(name="ph_product_sku")
public class ProductSku implements Serializable  {

	private static final long serialVersionUID = -6210699918665583521L;

	/** 主键 */
	@Id
	private Long id;

	/** 商品id */
	@Column(name="productId")
	private Long productId;

	/** 规格组合名称 */
	@Column(name="skuName")
	private String skuName;

	/** 参考价 */
	@Column(name="referencePrice")
	private BigDecimal referencePrice;

	/** 零售价 */
	@Column(name="retailPrice")
	private BigDecimal retailPrice;

	/** 进货价 */
	@Column(name="purchasePrice")
	private BigDecimal purchasePrice;

	/** 结算价 */
	@Column(name="settlementPrice")
	private BigDecimal settlementPrice;

	/** 市级起批数量 */
	@Column(name="municipalBatchQuantity")
	private Integer municipalBatchQuantity;

	/** 商户起批数量 */
	@Column(name="sellerbAtchQuantity")
	private Integer sellerbAtchQuantity;

	/** 起售数量 */
	@Column(name="saleQuantity")
	private Integer saleQuantity;

	/** 包邮数量 */
	@Column(name="numberOfPackages")
	private Integer numberOfPackages;

	/** 物流费用 */
	@Column(name="freight")
	private BigDecimal freight;

	/** 库存（数量） */
	@Column(name="skuCount")
	private Integer skuCount;

	/** 创建人 */
	@Column(name="createrId")
	private Long createrId;

	/** 创建时间 */
	@Column(name="createTime")
	private Date createTime;

	/** 规格值组合id(为商城按规格查询使用) */
	@Column(name="specificationValIds")
	private String specificationValIds;

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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName == null ? null : skuName.trim();
	}

	public BigDecimal getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(BigDecimal referencePrice) {
		this.referencePrice = referencePrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Integer getMunicipalBatchQuantity() {
		return municipalBatchQuantity;
	}

	public void setMunicipalBatchQuantity(Integer municipalBatchQuantity) {
		this.municipalBatchQuantity = municipalBatchQuantity;
	}

	public Integer getSellerbAtchQuantity() {
		return sellerbAtchQuantity;
	}

	public void setSellerbAtchQuantity(Integer sellerbAtchQuantity) {
		this.sellerbAtchQuantity = sellerbAtchQuantity;
	}

	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Integer getNumberOfPackages() {
		return numberOfPackages;
	}

	public void setNumberOfPackages(Integer numberOfPackages) {
		this.numberOfPackages = numberOfPackages;
	}

	

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Integer getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(Integer skuCount) {
		this.skuCount = skuCount;
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

	public String getSpecificationValIds() {
		return specificationValIds;
	}

	public void setSpecificationValIds(String specificationValIds) {
		this.specificationValIds = specificationValIds == null ? null : specificationValIds.trim();
	}
}