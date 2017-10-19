package com.alqsoft.mybatis.dao.hotRecommend;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.stereotype.Component;

/**
 * @author xuejz
 * @date 2017-03-30 10:00
 * @since 1.8
 */
@MyBatisRepository
@Component("mybatisHotRecommend")
public interface HotRecommendDao {

    int delete(Long id);
}
