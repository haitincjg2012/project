package com.alqsoft.service.member;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.member.Member;

public interface MemberService extends BaseService<Member> {
	
	public Member getRowLock(Long id);
}
