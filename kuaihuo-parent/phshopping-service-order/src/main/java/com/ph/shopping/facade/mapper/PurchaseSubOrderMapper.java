package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.QueryPurchaseOrderDTO;
import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO;
import com.ph.shopping.common.core.base.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 进货订单mapper
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Repository
public interface PurchaseSubOrderMapper extends BaseMapper<PurchaseSubOrder>{

    /**
     * @methodname selectPurchaseOrderListByPage 的描述：分页查询供应链进货订单
     * @param queryPurchaseOrderDTO
     * @return java.util.List<cm.ph.shopping.facade.order.vo.PurchaseSubOrderPageVO>
     * @author 郑朋
     * @create 2017/5/15
     */
    List<PurchaseSubOrderPageVO> selectPurchaseOrderListByPage(QueryPurchaseOrderDTO queryPurchaseOrderDTO);


}
