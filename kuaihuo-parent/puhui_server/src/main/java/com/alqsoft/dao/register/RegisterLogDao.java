package com.alqsoft.dao.register;

import org.alqframework.orm.hibernate.BaseDao;

import com.alqsoft.entity.log.UserLog;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月8日 上午10:24:56
 * Copyright 
 */
public interface RegisterLogDao extends BaseDao<UserLog> {
	UserLog save(UserLog userlog);

}
