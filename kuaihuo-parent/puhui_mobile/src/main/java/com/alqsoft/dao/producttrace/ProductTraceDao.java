package com.alqsoft.dao.producttrace;

import com.alqsoft.vo.ProductTraceVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-07 13:50
 */
@MyBatisRepository
public interface ProductTraceDao  {

    List<ProductTraceVO>  getTrace(@Param("orderNo")String orderNo);

    List<Map<String,Object>> getAttachmentList(Long pid);
}
