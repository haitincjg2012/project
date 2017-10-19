package com.alqsoft.service.impl.dashomedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.mybatis.dao.adshomedata.AdsHomeDataDao;
import com.alqsoft.service.adshomedata.AdsHomeDataService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月20日 上午12:17:32
 * Copyright 
 */
@Transactional(readOnly=true)
@Service
public class AdsHomeDataServiceImpl implements AdsHomeDataService{
	
	@Autowired
	private  AdsHomeDataDao adsHomeDataDao;
	@Override
	public List<Map<String, Object>> findIndustryTypeListMybatis(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return adsHomeDataDao.findIndustryTypeListMybatis(params);
	}

	@Override
	public int getIndustryTypeListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return adsHomeDataDao.getIndustryTypeListCount(params);
	}

}
