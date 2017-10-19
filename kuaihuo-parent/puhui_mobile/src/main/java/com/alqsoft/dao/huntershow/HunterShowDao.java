package com.alqsoft.dao.huntershow;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
@MyBatisRepository
public interface HunterShowDao {

	public List<Map<String,Object>> getHunterShowList(Long id);

	public List<Map<String,Object>> getImgList(Long id);

	public List<Map<String,Object>> getproductList(Long id);

	public List<Map<String,Object>> getRoleList(Long id);

	public Integer getcollectionType(Map<String,Object> params);

	public List<Map<String, Object>> getImgList3(Long id);


}
