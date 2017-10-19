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
public interface PurchaseOrderRefundAppliMapper extends BaseMapper<PurchaseOrderRefundAppli>{

    /**
     * @methodname updateRefund 的描述：根据订单id修改退款申请
     * @param purchaseOrderRefundAppli
     * @return int
     * @author 郑朋
     * @create 2017/5/16
     */
    int updateRefund(PurchaseOrderRefundAppli purchaseOrderRefundAppli);

    /**
     * @methodname selectRefund 的描述：根据订单id查询id
     * @param subOrderId
     * @return int
     * @author 郑朋
     * @create 2017/5/16
     */
    Long selectRefund(Long subOrderId);
}
