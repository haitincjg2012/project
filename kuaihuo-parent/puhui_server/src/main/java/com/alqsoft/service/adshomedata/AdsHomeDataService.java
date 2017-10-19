package com.alqsoft.service.adshomedata;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月20日 上午12:15:25
 * Copyright 
 */
@MyBatisRepository
public interface AdsHomeDataService {
	/***
	 * 获取数据列表
	 * @return
	 */
	public List<Map<String, Object>> findIndustryTypeListMybatis(Map<String,Object> params);
	/***
	 * 获取总的数据
	 */
	public int getIndustryTypeListCount(Map<String,Object> params);
}
