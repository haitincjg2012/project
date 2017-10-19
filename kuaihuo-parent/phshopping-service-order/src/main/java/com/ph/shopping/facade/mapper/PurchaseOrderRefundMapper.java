package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.entity.PurchaseOrderRefundAppli;
import com.ph.shopping.common.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 退款申请mapper
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Repository
public interface PurchaseOrderRefundMapper extends BaseMapper<PurchaseOrderRefundAppli>{

    /**
     * @methodname selectApplicationByOrderId 的描述：根据订单id查询退款详情
     * @param orderId
     * @return java.util.List<cm.ph.shopping.facade.order.entity.PurchaseOrderRefundAppli>
     * @author 郑朋
     * @create 2017/5/16
     */
    List<PurchaseOrderRefundAppli> selectApplicationByOrderId(Long orderId);

    /**
     * @methodname deleteRefundByOrderId 的描述：根据订单id删除退款详情
     * @param orderId
     * @return int
     * @author 郑朋
     * @create 2017/5/16
     */
    int deleteRefundByOrderId(Long orderId);
}
