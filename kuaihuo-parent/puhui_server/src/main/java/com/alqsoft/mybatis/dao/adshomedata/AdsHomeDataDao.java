package com.alqsoft.mybatis.dao.adshomedata;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月20日 上午12:25:28
 * Copyright 
 */
@MyBatisRepository
public interface AdsHomeDataDao {
	
   public List<Map<String,Object>> findIndustryTypeListMybatis(Map<String,Object> params);
	
   public int getIndustryTypeListCount(Map<String,Object> params);

}
