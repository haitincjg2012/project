package com.alqsoft.rpc.mobile;

import com.alqsoft.entity.producttrace.ProductTrace;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-07 13:37
 */
public interface RpcProductTraceService extends BaseService<ProductTrace> {

    Result addTrace(Long id, String orderNo, String content, String ids);
}
