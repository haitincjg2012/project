package com.alqsoft.dao.collectionproduct;

import com.alqsoft.vo.CollectionProductVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:28
 */
@MyBatisRepository
public interface CollectionProductDao {

    CollectionProductVO getCollectionProduct(@Param("mid") Long id, @Param("pid") Long pid);

    List<Map<String,Object>> getCollectionProductList(Map<String,Object> map);
}
