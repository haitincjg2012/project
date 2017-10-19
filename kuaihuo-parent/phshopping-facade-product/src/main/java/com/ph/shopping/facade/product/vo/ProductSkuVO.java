package com.ph.shopping.facade.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductSkuVO implements Serializable {
	
	private static final long serialVersionUID = -8674429267451461991L;

		/** 主键 */
	    private Long id;

	    /** 商品id */
	    private Long productId;

	    /** 规格组合名称 */
	    private String skuName;

	    /** 参考价 */
	    private BigDecimal referencePrice;

	    /** 零售价 */
	    private BigDecimal retailPrice;

	    /** 进货价 */
	    private BigDecimal purchasePrice;

	    /** 结算价 */
	    private BigDecimal settlementPrice;

	    /** 市级起批数量 */
	    private Integer municipalBatchQuantity;

	    /** 商户起批数量 */
	    private Integer sellerbAtchQuantity;

	    /** 起售数量 */
	    private Integer saleQuantity;

	    /** 包邮数量 */
	    private Integer numberOfPackages;

	    /** 物流费用 */
	    private BigDecimal freight;

	    /** 库存（数量） */
	    private Integer skuCount;

	    /** 规格值组合id(为商城按规格查询使用) */
	    private String specificationValIds;

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

	    public String getSpecificationValIds() {
	        return specificationValIds;
	    }

	    public void setSpecificationValIds(String specificationValIds) {
	        this.specificationValIds = specificationValIds == null ? null : specificationValIds.trim();
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
