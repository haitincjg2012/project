package com.alqsoft.service.impl.producttraceattachment;

import com.alqsoft.dao.producttraceattachment.ProductTraceAttachmentDao;
import com.alqsoft.entity.producttraceattachment.ProductTraceAttachment;
import com.alqsoft.service.producttraceattachment.ProductTraceAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单跟踪附件
 * @author Xuejizheng
 * @date 2017-03-07 11:51
 */
@Service
@Transactional
public class ProductTraceAttachmentServiceImpl implements ProductTraceAttachmentService {

    @Autowired
    private ProductTraceAttachmentDao productTraceAttachmentDao;

    @Override
    public ProductTraceAttachment saveAndModify(ProductTraceAttachment productTraceAttachment) {
        return productTraceAttachmentDao.save(productTraceAttachment);
    }

    @Override
    public boolean delete(Long aLong) {
        productTraceAttachmentDao.delete(aLong);
        return false;
    }

    @Override
    public ProductTraceAttachment get(Long aLong) {
        return productTraceAttachmentDao.findOne(aLong);
    }
}
