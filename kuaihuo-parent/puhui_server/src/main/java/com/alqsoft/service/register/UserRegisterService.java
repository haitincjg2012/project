package com.alqsoft.service.register;

import org.alqframework.result.Result;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.log.UserLog;
import com.alqsoft.entity.member.Member;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午10:50:54
 * Copyright 
 */
public interface UserRegisterService {
	
	public Member save(Member member);
	
	public Result save(UserLog userLog);
	
	public Result save(Hunter hunter);
	
	public Result updateMemberById(Member  member,Long id);

}
