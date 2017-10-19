package com.alqsoft.service.producttrace;

import com.alqsoft.entity.producttrace.ProductTrace;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-07 11:43
 */
public interface ProductTraceService extends BaseService<ProductTrace> {

    Result addTrace(Long id, String orderNo, String content, String ids);
}
