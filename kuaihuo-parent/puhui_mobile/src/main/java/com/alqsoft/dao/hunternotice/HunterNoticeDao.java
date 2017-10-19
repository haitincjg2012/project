package com.alqsoft.dao.hunternotice;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface HunterNoticeDao {

    List<Map> getHunterNoticeList(@Param("id") Long id);


}
