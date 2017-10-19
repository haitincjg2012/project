package com.alqsoft.service.producttrace;

import com.alqsoft.vo.ProductTraceVO;
import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-07 14:53
 */
public interface ProductTraceService {

    Result  add (Long uid, String orderNo, String content, MultipartFile firstFile,MultipartFile secondFile,MultipartFile thirdFile);

    List<ProductTraceVO> getTrace(String orderNo);

    List<Map<String,Object>> getAttachmentList(Long pid);

    Result addProductTrace(Long id, String orderNo, String content, String ids);
}
