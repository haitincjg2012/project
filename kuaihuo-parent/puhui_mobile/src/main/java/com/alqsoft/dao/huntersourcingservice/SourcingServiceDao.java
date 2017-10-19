package com.alqsoft.dao.huntersourcingservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SourcingServiceDao {

	List<Map<String, Object>> getSourcingServiceList(HashMap<String, Object> param);

}
