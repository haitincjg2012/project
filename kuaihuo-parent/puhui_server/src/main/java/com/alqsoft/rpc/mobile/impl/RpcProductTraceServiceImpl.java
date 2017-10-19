package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.alqsoft.entity.producttrace.ProductTrace;
import com.alqsoft.rpc.mobile.RpcProductTraceService;
import com.alqsoft.service.producttrace.ProductTraceService;

/**
 * @author Xuejizheng
 * @date 2017-03-07 13:37
 */
@Service
@Transactional
public class RpcProductTraceServiceImpl implements RpcProductTraceService{

    @Autowired
    private ProductTraceService productTraceService;

    @Override
    public ProductTrace saveAndModify(ProductTrace productTrace) {
    	productTrace.setContent(HtmlUtils.htmlUnescape(productTrace.getContent()));
    	return productTraceService.saveAndModify(productTrace);
    }

    @Override
    public boolean delete(Long aLong) {
        return productTraceService.delete(aLong);
    }

    @Override
    public ProductTrace get(Long aLong) {
        return productTraceService.get(aLong);
    }

    @Override
    public Result addTrace(Long id, String orderNo, String content, String ids) {
        return productTraceService.addTrace(id,orderNo,content,ids);
    }
}
