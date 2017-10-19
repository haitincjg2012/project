package com.alqsoft.dao.area;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface AreaDao {

	public List<Map<String, Object>> findProList();

	public List<Map<String, Object>> findCityListByPId(Long pId);

	public List<Map<String, Object>> getTownList(Long pId);

}
