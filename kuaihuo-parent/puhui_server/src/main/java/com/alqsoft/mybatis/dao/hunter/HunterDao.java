package com.alqsoft.mybatis.dao.hunter;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-18 17:06
 */
@MyBatisRepository
@Component("mybatisHunterDao")
public interface HunterDao {

    List<Map<String,Object>>  getHunterByIndustryType(Map<String,Object> map);

    Map<String,Object> getHunterInfo(Long hid);
}
