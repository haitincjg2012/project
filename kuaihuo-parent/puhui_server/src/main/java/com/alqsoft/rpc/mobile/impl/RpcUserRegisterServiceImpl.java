package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcUserRegisterService;
import com.alqsoft.service.register.UserRegisterService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午10:47:08
 * Copyright 
 */
@Transactional
@Service
public class RpcUserRegisterServiceImpl implements RpcUserRegisterService {

	@Autowired
	private UserRegisterService userRegisterService;

	@Override
	public Member save(Member member) {
		// TODO Auto-generated method stub
		return userRegisterService.save(member);
	}

	@Override
	public Result save(UserLog userLog) {
		// TODO Auto-generated method stub
		return userRegisterService.save(userLog);
	}
	
	@Override
	public Result save(Hunter hunter) {
		// TODO Auto-generated method stub
		return userRegisterService.save(hunter);
	}

	@Override
	public Result updateMember(Member member, Long id) {
		// TODO Auto-generated method stub
		return userRegisterService.updateMemberById(member,id);
	}

	
	
	
	
	

}
