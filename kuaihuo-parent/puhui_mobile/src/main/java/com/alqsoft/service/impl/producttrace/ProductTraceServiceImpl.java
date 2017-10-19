package com.alqsoft.service.impl.producttrace;

import com.alqsoft.dao.producttrace.ProductTraceDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.producttrace.ProductTrace;
import com.alqsoft.entity.producttraceattachment.ProductTraceAttachment;
import com.alqsoft.rpc.mobile.RpcProductTraceAttachmentService;
import com.alqsoft.rpc.mobile.RpcProductTraceService;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.producttrace.ProductTraceService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.utils.UploadFileName;
import com.alqsoft.vo.ProductTraceVO;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 订单跟踪
 * @author Xuejizheng
 * @date 2017-03-07 15:04
 */
@Service
@Transactional
public class ProductTraceServiceImpl implements ProductTraceService {

    private static Logger log = LoggerFactory.getLogger(ProductTraceServiceImpl.class);

    @Autowired
    private RpcProductTraceService rpcProductTraceService;

    @Autowired
    private RpcProductTraceAttachmentService rpcProductTraceAttachmentService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ProductTraceDao productTraceDao;

    /**
     * 添加订单跟踪信息
     * @param uid
     * @param orderNo
     * @param content
     * @param firstFile
     * @param secondFile
     * @return
     */
    @Override
    public Result add(Long uid, String orderNo, String content, MultipartFile firstFile,
                      MultipartFile secondFile,MultipartFile thirdFile) {

        if (Objects.isNull(uid) || StringUtils.isBlank(orderNo) || StringUtils.isBlank(content)){
                return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        ProductTrace productTrace = new ProductTrace();
        productTrace.setContent(content);
        productTrace.setOrderNo(orderNo);
        try {
            ProductTrace resultProductTrace=rpcProductTraceService.saveAndModify(productTrace);

            String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
            String module= UploadFileName.PRODUCT_TRACE.getName();
            ProductTraceAttachment productTraceAttachment = new ProductTraceAttachment();
            productTraceAttachment.setProductTrace(resultProductTrace);

            if (firstFile!=null){
                if (uploadFile(firstFile, resultProductTrace, extendFile, module, productTraceAttachment, attachmentService, rpcProductTraceAttachmentService))
                    return ResultUtils.returnError("附件上传失败");
            }

            if (secondFile!=null){
                if (uploadFile(secondFile, resultProductTrace, extendFile, module, productTraceAttachment, attachmentService, rpcProductTraceAttachmentService))
                    return ResultUtils.returnError("附件上传失败");
            }

            if (thirdFile!=null){
                if (uploadFile(thirdFile, resultProductTrace, extendFile, module, productTraceAttachment, attachmentService, rpcProductTraceAttachmentService))
                    return ResultUtils.returnError("附件上传失败");
            }

            return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }

    }

    private static boolean uploadFile(MultipartFile file, ProductTrace resultProductTrace, String[] extendFile, String module, ProductTraceAttachment productTraceAttachment, AttachmentService attachmentService, RpcProductTraceAttachmentService rpcProductTraceAttachmentService) {
        if (file != null) {
            productTraceAttachment.setProductTrace(resultProductTrace);
            Result secondResult =attachmentService.mobileUploadAttachment(file,new Object[]{attachmentService,"saveAttachment"},module,extendFile);
            if ("1".equals(String.valueOf(secondResult.getCode()))){
                Attachment attachment = (Attachment) secondResult.getContent();
                productTraceAttachment.setAddress(attachment.getAddress());
                productTraceAttachment.setName(attachment.getName());
                rpcProductTraceAttachmentService.saveAndModify(productTraceAttachment);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ProductTraceVO> getTrace(String orderNo) {
        return productTraceDao.getTrace(orderNo);
    }

    @Override
    public List<Map<String,Object>> getAttachmentList(Long pid) {
        return productTraceDao.getAttachmentList(pid);
    }

    /**
     * 添加跟蹤信息 IOS
     * @param id
     * @param orderNo
     * @param content
     * @param ids
     * @return
     */
    @Override
    public Result addProductTrace(Long id, String orderNo, String content, String ids) {
        if (Objects.isNull(id) || StringUtils.isBlank(orderNo) || StringUtils.isBlank(content)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        return rpcProductTraceService.addTrace(id,orderNo,content,ids);

    }
}
