package com.ph.shopping.facade.product.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @项目：phshopping-facade-product
 * @描述：订单查询商品信息
 * @作者： Mr.zheng
 * @创建时间：2017-03-24
 * @Copyright @2017 by Mr.zheng
 */
public class OrderProductDTO implements Serializable{

    private static final long serialVersionUID = 1045479878529163006L;
    /**
     * 供应商id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 商品信息集合
     */
    private List<ProductDTO> phProductVoList;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<ProductDTO> getPhProductVoList() {
        return phProductVoList;
    }

    public void setPhProductVoList(List<ProductDTO> phProductVoList) {
        this.phProductVoList = phProductVoList;
    }
}
