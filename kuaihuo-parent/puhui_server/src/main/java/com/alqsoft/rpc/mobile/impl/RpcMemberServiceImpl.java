package com.alqsoft.rpc.mobile.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcMemberService;
import com.alqsoft.service.member.MemberService;


/**
 * Date:     2017年3月6日  15:50:41 <br/>
 * @author   dinglanlan
 * @version  会员
 * @see 	 
 */

@Controller
public class RpcMemberServiceImpl implements RpcMemberService {

	@Autowired
	private MemberService memberService;

	@Override
	public Member saveMember(Member member) {
		// TODO Auto-generated method stub
		return memberService.saveAndModify(member);
	}
	

}
