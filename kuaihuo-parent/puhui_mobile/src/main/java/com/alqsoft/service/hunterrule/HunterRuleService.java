package com.alqsoft.service.hunterrule;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

public interface HunterRuleService {

	public Result getRoleList(Member member, Integer page, Integer rows);

}
