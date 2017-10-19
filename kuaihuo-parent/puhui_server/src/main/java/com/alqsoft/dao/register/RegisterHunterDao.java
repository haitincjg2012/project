package com.alqsoft.dao.register;

import org.alqframework.orm.hibernate.BaseDao;

import com.alqsoft.entity.hunter.Hunter;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月8日 上午11:26:42
 * Copyright 
 */
public interface RegisterHunterDao extends BaseDao<Hunter> {
	Hunter save(Hunter hunter);

}
