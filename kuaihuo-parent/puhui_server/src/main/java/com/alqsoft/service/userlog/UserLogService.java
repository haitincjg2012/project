package com.alqsoft.service.userlog;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.log.UserLog;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午5:46:09
 * Copyright 
 */
public interface UserLogService extends BaseService<UserLog>{
	
	UserLog saveAndModify(UserLog userLog);

	
}
