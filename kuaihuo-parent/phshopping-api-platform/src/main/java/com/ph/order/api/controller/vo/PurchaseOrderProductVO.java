package com.ph.order.api.controller.vo;

import com.ph.shopping.facade.product.vo.ProductVO;

/**
 * 订单新增页面商品VO
 *
 * @author 郑朋
 * @create 2017/6/1
 **/
public class PurchaseOrderProductVO extends ProductVO {

    private static final long serialVersionUID = -1833657964115438449L;

    /**
     * 商品快照id
     */
    private Long productSnapId;

    public Long getProductSnapId() {
        return productSnapId;
    }

    public void setProductSnapId(Long productSnapId) {
        this.productSnapId = productSnapId;
    }
}
