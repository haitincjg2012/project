package com.alqsoft.dao.collectionhunter;

import com.alqsoft.vo.CollectionHunterVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:28
 */
@MyBatisRepository
public interface CollectionHunterDao {


    CollectionHunterVO getCollectionHunter(@Param("mid") Long id,@Param("hid") Long hid);

    List<Map<String,Object>> getCollectionHunterList(Map<String, Object> map);
}
