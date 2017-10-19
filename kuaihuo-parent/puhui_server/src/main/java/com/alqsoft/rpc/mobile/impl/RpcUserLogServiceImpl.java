package com.alqsoft.rpc.mobile.impl;

import org.slf4j.Logger;
import org.alqframework.result.Result;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.log.UserLog;
import com.alqsoft.rpc.mobile.RpcUserLogService;
import com.alqsoft.service.log.UserLogService;



/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月3日 下午4:59:36
 * Copyright 
 */
@Transactional
@Service
public class RpcUserLogServiceImpl implements RpcUserLogService{
	private static Logger logger=LoggerFactory.getLogger(RpcUserLogServiceImpl.class);

	@Autowired
	private UserLogService userLogService;

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserLog get(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result save(UserLog userLog) {
		Result result=new Result();
		try {
			UserLog userLogs=userLogService.saveAndModify(userLog);
			result.setCode(1);
			result.setMsg("保存用户登录信息成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存用户登录信息失败!"+"登录角色"+userLog.getRoleName()+"登录名为："+userLog.getLoginName()+"登录时间："+userLog.getLoginTime());
			result.setCode(0);
			result.setMsg("保存用户登录信息失败");
			return result;
		}
	}

	@Override
	public UserLog saveAndModify(UserLog arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
