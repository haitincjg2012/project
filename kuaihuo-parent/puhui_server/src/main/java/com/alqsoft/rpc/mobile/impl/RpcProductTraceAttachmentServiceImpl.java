package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.producttraceattachment.ProductTraceAttachment;
import com.alqsoft.rpc.mobile.RpcProductTraceAttachmentService;
import com.alqsoft.service.producttraceattachment.ProductTraceAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-07 13:37
 */
@Service
@Transactional
public class RpcProductTraceAttachmentServiceImpl implements RpcProductTraceAttachmentService{

    @Autowired
    private ProductTraceAttachmentService productTraceAttachmentService;

    @Override
    public ProductTraceAttachment saveAndModify(ProductTraceAttachment productTrace) {
        return productTraceAttachmentService.saveAndModify(productTrace);
    }

    @Override
    public boolean delete(Long aLong) {
        return productTraceAttachmentService.delete(aLong);
    }

    @Override
    public ProductTraceAttachment get(Long aLong) {
        return productTraceAttachmentService.get(aLong);
    }
}
