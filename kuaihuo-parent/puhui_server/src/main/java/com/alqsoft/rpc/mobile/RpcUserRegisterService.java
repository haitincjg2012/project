package com.alqsoft.rpc.mobile;

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
 * @create-time 2017年3月7日 下午10:45:22
 * Copyright 
 */
public interface RpcUserRegisterService {
	
	public Member save(Member member);
	
	public Result save(UserLog userLog);
	
	public Result save(Hunter hunter);
	
	public Result updateMember(Member member,Long id);
	
	

}
