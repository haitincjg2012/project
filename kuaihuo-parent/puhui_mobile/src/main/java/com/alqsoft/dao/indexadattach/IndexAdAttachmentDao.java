package com.alqsoft.dao.indexadattach;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;



/**
 * 
 * @Description: 获取首页数据，广告滚动至展示
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年2月28日 下午4:17:42
 * Copyright 
 */
@MyBatisRepository
public interface IndexAdAttachmentDao {

public	List<Map> getIndexAdAttachDao(Integer startInt,Integer endInt);

public  Map getTextDetail(Long id);
	
}
