/**
 *
 */
package com.ph.shopping.facade.spm.entity;

import com.ph.shopping.common.core.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @项目：phshopping-facade-agent_purchase_order
 * @描述：代理进货单实体
 * @作者： Mr.Shu
 * @创建时间：2017年3月13日
 * @Copyright @2017 by Mr.Shu
 */
@Table(name = "ph_agent_purchase_order")
public class AgentPurchaseOrder extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3495851720854967797L;

	@Column(name = "orderNumber")
    private String orderNumber;

    @Column(name = "supplierId")
    private String supplierId;

    @Column(name = "personName")
    private String personName;

    @Column(name = "personTel")
    private String personTel;

    @Column(name = "productNum")
    private Integer productNum;

    @Column(name = "productTotalPrice")
    private Long productTotalPrice;

    @Column(name = "productPrice")
    private Long productPrice;

    @Column(name = "logisticsFee")
    private Long logisticsFee;

    @Column(name = "orderTotalPrice")
    private Long orderTotalPrice;

    @Column(name = "createId")
    private Long createId;

    @Column(name = "logisticsCompany")
    private String logisticsCompany;

    @Column(name = "address")
    private String address;

    @Column(name = "logisticsNumber")
    private String logisticsNumber;

    @Column(name = "productId")
    private String productId;

    @Column(name = "purchasePersonId")
    private String purchasePersonId;

    @Column(name = "status")
    private Integer status;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonTel() {
        return personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Long getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(Long productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(Long logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public Long getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Long orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurchasePersonId() {
        return purchasePersonId;
    }

    public void setPurchasePersonId(String purchasePersonId) {
        this.purchasePersonId = purchasePersonId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
