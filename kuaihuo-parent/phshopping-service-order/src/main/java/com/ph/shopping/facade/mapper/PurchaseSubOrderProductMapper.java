package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ph.shopping.common.core.base.BaseMapper;

import cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct;

/**
 * 供应链子订单对应的商品mapper
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Repository
public interface PurchaseSubOrderProductMapper extends BaseMapper<PurchaseSubOrderProduct> {

    /**
     * @methodname selPurSubOrdProBySubIdGroup 的描述：根据供应链子订单id查询子订单对应的商品信息(商品id进行分组)
     * @param subOrderId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct>
     * @author 郑朋
     * @create 2017/5/16
     */
    List<PurchaseSubOrderProduct> selPurSubOrdProBySubIdGroup (Long subOrderId);

    /**
     * @methodname selPurSubOrdProBySubId 的描述：根据供应链子订单id查询子订单对应的商品信息（查询所有的sku信息）
     * @param subOrderId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct>
     * @author 郑朋
     * @create 2017/5/16
     */
    List<PurchaseSubOrderProduct> selPurSubOrdProBySubId (Long subOrderId);

    /**
     * @methodname selectPurSubOrderSkuBySubId 的描述：根据供应链商品id查询子订单对应的商品sku信息
     * @param subOrderId
     * @param productId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct>
     * @author 郑朋
     * @create 2017/5/16
     */
    List<PurchaseSubOrderProduct> selectPurSubOrderSkuBySubId (@Param("subOrderId") Long subOrderId,
                                                               @Param("productId") Long productId);

    /**
     * @methodname insertPurchaseOrderProduct 的描述：新增订单商品信息
     * @param purchaseSubOrderProductList
     * @param subOrderId
     * @return int
     * @author 郑朋
     * @create 2017/5/17
     */
    int insertPurchaseOrderProduct(@Param(value = "list") List<PurchaseSubOrderProduct> purchaseSubOrderProductList,
                                 @Param(value = "subOrderId") Long subOrderId);
}
