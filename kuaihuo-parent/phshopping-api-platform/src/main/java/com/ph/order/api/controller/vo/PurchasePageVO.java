package com.ph.order.api.controller.vo;

import cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO;

/**
 * 供货订单分页vo
 *
 * @author 郑朋
 * @create 2017/5/24
 **/
public class PurchasePageVO extends PurchaseSubOrderPageVO {

    private static final long serialVersionUID = 6213670103448430446L;

    /**
     * 供应商
     */
    private String supplierName;

    /**
     * 进货商
     */
    private String purchaseName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }
}
