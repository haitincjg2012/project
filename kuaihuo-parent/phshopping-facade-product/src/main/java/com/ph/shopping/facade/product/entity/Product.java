package com.ph.shopping.facade.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * PhProduct 商品实体类
 *
 * @version: 2.1更新
 * @author: 李超
 * @date: 2017-05-12 10:45:37
 */
@Table(name="ph_product")
public class Product extends BaseEntity  implements Serializable{

    private static final long serialVersionUID = 8774795602698574422L;


    /** 商品名称 */
    @Column(name="productName")
    @NotBlank(message="[商品名称]不可为空")
    @Length(max=200,message="[商品名称]最大长度为200个字符")
    private String productName;

    /** 商品总数量 */
    @Column(name="productCount")
    private Integer productCount;

    /** 商品描述 */
    @Column(name="description")
    private String description;

    /** 商品类别id */
    @Column(name="productClassifyId")
    private Long productClassifyId;

    /** 上架下架状态 0 上架 1未上架 */
    @Column(name="saleNoSaleStatus")
    private Byte saleNoSaleStatus;

    /** 审核状态 0待审核 1未通过 2通过*/
    @Column(name="auditState")
    private Byte auditState;

    /** 删除标记 0未删除 1已删除 */
    @Column(name="isDelete")
    private Byte isDelete;

    /** 商品来源 1 平台 2 代理商 */
    @Column(name="productForm")
    private Byte productForm;

    /** 条形码 */
    @Column(name="barCode")
    private String barCode;

    /** 供应商id */
    @Column(name="supplierId")
    private Long supplierId;

    /** 类型区分1.全国，2本地 */
    @Column(name="productType")
    private Byte productType;
    
    /** 商品销量（为供应链和商城的总销量） */
    @Column(name="commoditySales")
    private  Integer  commoditySales;


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

	public Integer getCommoditySales() {
		return commoditySales;
	}

	public void setCommoditySales(Integer commoditySales) {
		this.commoditySales = commoditySales;
	}

    
}