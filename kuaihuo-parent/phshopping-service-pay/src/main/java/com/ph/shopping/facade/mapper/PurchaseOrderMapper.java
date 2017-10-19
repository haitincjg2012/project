package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.QueryPurchaseOrderDTO;
import cm.ph.shopping.facade.order.entity.PurchaseMainOrder;
import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.vo.UnlineOrderVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 进货订单mapper
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Repository
public interface PurchaseOrderMapper extends BaseMapper<PurchaseSubOrder>{

    /**
     * @methodname selPurSubOrderByOrderNo 的描述：通过子订单号查询订单信息
     * @param orderNo
     * @return cm.ph.shopping.facade.order.entity.PurchaseSubOrder
     * @author 郑朋
     * @create 2017/5/18
     */
    PurchaseSubOrder selPurSubOrderByOrderNo(@Param("orderNo") String orderNo);


    /**
     * @methodname selPurSubOrderByMainOrd 的描述：通过主订单id查询子订单数据
     * @param mainOrderId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseSubOrder>
     * @author 郑朋
     * @create 2017/5/19
     */
    List<PurchaseSubOrder> selPurSubOrderByMainOrd(@Param("mainOrderId") Long mainOrderId);

    /**
     * @methodname selPurMainOrderByOrderNo 的描述：通过主订单号查询订单信息
     * @param orderNo
     * @return cm.ph.shopping.facade.order.entity.PurchaseMainOrder
     * @author 郑朋
     * @create 2017/5/18
     */
    PurchaseMainOrder selPurMainOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * @methodname updatePurSubOrder 的描述：通过主订单修改子订单状态
     * @param purchaseSubOrder
     * @return int
     * @author 郑朋
     * @create 2017/5/19
     */
    int updatePurSubOrder(PurchaseSubOrder purchaseSubOrder);

    /**
     * @methodname updatePurMainOrder 的描述：通过主订单id修改订单信息
     * @param purchaseMainOrder
     * @return int
     * @author 郑朋
     * @create 2017/5/19
     */
    int updatePurMainOrder(PurchaseMainOrder purchaseMainOrder);

    /**
     * @methodname selPurSubOrdProBySubId 的描述：根据供应链子订单id查询子订单对应的商品信息（查询所有的sku信息）
     * @param subOrderId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseSubOrderProduct>
     * @author 郑朋
     * @create 2017/5/16
     */
    List<PurchaseSubOrderProduct> selPurSubOrdProBySubId (Long subOrderId);
    /**
     * @desc:根据订单id 查询支付订单
     * 创建人：王雪洋   
     * 创建时间：2017年8月27日 下午12:09:33    
     * @param id
     * @return unlineOrderVO
     */
   public UnlineOrderVO selectUnlineOrderVO(@Param("id") Long id);
}
