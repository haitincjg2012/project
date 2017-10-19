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
 * @param <T>
 * @copyright 2010-2015
 * @create-time 2017年3月7日 下午9:29:10
 * Copyright 
 */
public interface RpcUserRegisterService  {
	
	/***
	 * 会员注册信息
	 * @param member
	 * @return
	 */
	public Member save(Member member);
	
	/***
	 * 用户登录信息
	 * @param userLog
	 * @return
	 */
	public Result save(UserLog userLog);
	
	/***
	 * 批发商信息
	 * @param hunter
	 * @return
	 */
	public Result save(Hunter hunter);
	/**
	 * 通过id修改Member
	 * @param member
	 * @param id
	 * @return
	 */
	public Result updateMember(Member member,Long id);

}
