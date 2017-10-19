package com.alqsoft.rpc.mobile;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.log.UserLog;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午4:57:19
 * Copyright 
 */
public interface RpcUserLogService extends BaseService<UserLog>{
	/**
	 * 保存用户登录信息
	 */
	public Result save(UserLog userLog);

	
}
