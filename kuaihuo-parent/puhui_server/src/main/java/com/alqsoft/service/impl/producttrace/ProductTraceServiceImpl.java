package com.alqsoft.service.impl.producttrace;

import com.alqsoft.dao.attachment.AttachmentDao;
import com.alqsoft.dao.producttrace.ProductTraceDao;
import com.alqsoft.dao.producttraceattachment.ProductTraceAttachmentDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.producttrace.ProductTrace;
import com.alqsoft.entity.producttraceattachment.ProductTraceAttachment;
import com.alqsoft.service.producttrace.ProductTraceService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

/**
 * @author Xuejizheng
 * @date 2017-03-07 11:48
 */
@Service
@Transactional
public class ProductTraceServiceImpl implements ProductTraceService {

    private static final Logger log = LoggerFactory.getLogger(ProductTraceServiceImpl.class);

    @Autowired
    private ProductTraceDao productTraceDao;

    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private ProductTraceAttachmentDao productTraceAttachmentDao;

    @Override
    public ProductTrace saveAndModify(ProductTrace productTrace) {
        return productTraceDao.save(productTrace);
    }

    @Override
    public boolean delete(Long aLong) {
        productTraceDao.delete(aLong);
        return false;
    }

    @Override
    public ProductTrace get(Long aLong) {
        return productTraceDao.findOne(aLong);
    }

    /**
     * 根据附件ID 添加跟踪信息
     * @param id
     * @param orderNo
     * @param content
     * @param ids
     * @return
     */
    @Override
    public Result addTrace(Long id, String orderNo, String content, String ids) {
        ProductTrace trace = new ProductTrace();
        trace.setContent(HtmlUtils.htmlUnescape(content));
        trace.setOrderNo(orderNo);
        try {
            ProductTrace resultTrace = productTraceDao.save(trace);
            if (StringUtils.isNotBlank(ids)){
                String[] attachmentIds = ids.split(",");
                for (String aid:attachmentIds){
                    if (StringUtils.isBlank(aid)){
                         continue;
                     }
                    ProductTraceAttachment productTraceAttachment = new ProductTraceAttachment();
                    productTraceAttachment.setProductTrace(resultTrace);
                    Attachment attachment = attachmentDao.findOne(Long.parseLong(aid));
                    if(Objects.isNull(attachment)){
                       return ResultUtils.returnError("附件不存在");
                    }
                    productTraceAttachment.setAddress(attachment.getAddress());
                    productTraceAttachment.setName(attachment.getName());
                    productTraceAttachmentDao.save(productTraceAttachment);
                }
            }
            return ResultUtils.returnSuccess("添加成功");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.returnError("接口调用失败");
        }

    }
}
