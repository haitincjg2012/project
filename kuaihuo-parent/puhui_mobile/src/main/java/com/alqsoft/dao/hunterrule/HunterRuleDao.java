package com.alqsoft.dao.hunterrule;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface HunterRuleDao {


	//public List<Map<String,Object>> getRoleList(@Param("hunter_id")Long id);
	List<Map<String, Object>> getRoleList(Map<String, Object> params);

}
