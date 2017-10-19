package com.ph.order.api.controller.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 订单商品Vo
 *
 * @author 郑朋
 * @create 2017/5/27
 **/
public class PurchaseProductVO implements Serializable {

    private static final long serialVersionUID = -120869437055653052L;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商id
     */
    private Long supplierId;

    /**
     * 商品信息
     */
    private List<PurchaseOrderProductVO> productVOList;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<PurchaseOrderProductVO> getProductVOList() {
        return productVOList;
    }

    public void setProductVOList(List<PurchaseOrderProductVO> productVOList) {
        this.productVOList = productVOList;
    }
}
