package com.ph.queue;

import cm.ph.shopping.facade.order.dto.AddPurchaseOrderDTO;
import cm.ph.shopping.facade.order.service.IPurchaseOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.queue.request.OrderTaskRequest;
import com.ph.shopping.common.util.result.Result;
import org.springframework.stereotype.Service;

/**
 * @项目：phshopping-api-platform
 * @描述：
 * @作者： Mr.zheng
 * @创建时间：2017-03-28
 * @Copyright @2017 by Mr.zheng
 */
@Service
public class OrderService {

    @Reference(version = "1.0.0",retries = 0)
    IPurchaseOrderService purchaseOrderService;

    @SuppressWarnings("rawtypes")
	public synchronized Result callBack(OrderTaskRequest request){
        return purchaseOrderService.addPurchaseOrder((AddPurchaseOrderDTO)request.getData());
    }
}
