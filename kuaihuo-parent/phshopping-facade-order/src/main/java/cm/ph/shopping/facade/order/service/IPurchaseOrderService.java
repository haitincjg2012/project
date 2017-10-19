package cm.ph.shopping.facade.order.service;

import cm.ph.shopping.facade.order.dto.*;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;

/**
 * 供应链订单接口
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
public interface IPurchaseOrderService {


    /**
     * @methodname getPurchaseOrderListByPage 的描述：分页查询供应链进货订单
     * @param pageBean
     * @param queryPurchaseOrderDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/15
     */
    Result getPurchaseOrderListByPage(PageBean pageBean, QueryPurchaseOrderDTO queryPurchaseOrderDTO);

    /**
     * @methodname getPurchaseOrderListByPage 的描述：查询供应链进货订单
     * @param queryPurchaseOrderDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/15
     */
    Result getPurchaseOrderList(QueryPurchaseOrderDTO queryPurchaseOrderDTO);

    /**
     * @methodname getPurchaseOrderDetail 的描述：查询订单详情
     * @param orderId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/15
     */
    Result getPurchaseOrderDetail(Long orderId);

    /**
     * @methodname getPurchaseOrderProductSku 的描述：查询订单商品的的sku信息
     * @param subOrderId
     * @param productId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/15
     */
    Result getPurchaseOrderProductSku(Long subOrderId, Long productId);

    /**
     * @methodname confirmReceipt 的描述：确认收货
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/15
     */
    Result confirmReceipt(UpdateOrderStatusDTO updateOrderStatusDTO);
    /**
     * @methodname addRefundApplication 的描述：新增申请退款
     * @param refundApplicationDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/16
     */
    Result addRefundApplication(RefundApplicationDTO refundApplicationDTO);

    /**
     * @methodname getRefundApplication 的描述：获取申请退款详情
     * @param orderId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/16
     */
    Result getRefundApplication(Long orderId);

    /**
     * @methodname cancelOrder 的描述：取消订单
     * @param updateOrderStatusDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/16
     */
    Result cancelOrder(UpdateOrderStatusDTO updateOrderStatusDTO);

    /**
     * @methodname addPurchaseOrder 的描述：新增供应链订单
     * @param addPurchaseOrderDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/16
     */
    Result addPurchaseOrder(AddPurchaseOrderDTO addPurchaseOrderDTO);

    /**
     * @methodname updatePurchaseOrderFreight 的描述：修改订单物流费用
     * @param updatePurOrderFreDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/17
     */
    Result updatePurchaseOrderFreight(UpdatePurOrderFreDTO updatePurOrderFreDTO);

    /**
     * @methodname sendProduct 的描述：订单发货
     * @param sendProductDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/16
     */
    Result sendProduct(SendProductDTO sendProductDTO);

    /**
     * @methodname checkRefundApplication 的描述：审核退款申请
     * @param checkRefundAppliDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/17
     */
    Result checkRefundApplication(CheckRefundAppliDTO checkRefundAppliDTO);

    /**
     * @methodname getMainOrderById 的描述：根据主订单id查询主订单信息
     * @param mainOrderId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/5
     */
    Result getMainOrderById(Long mainOrderId);

}
