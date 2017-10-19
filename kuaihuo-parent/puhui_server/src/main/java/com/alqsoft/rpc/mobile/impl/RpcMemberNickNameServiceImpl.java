package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.rpc.mobile.RpcMemberNickNameService;
import com.alqsoft.service.hunternickname.HunterNicknameService;
import com.alqsoft.service.membernickname.MemberNicknameService;

@Service
@Transactional
public class RpcMemberNickNameServiceImpl implements RpcMemberNickNameService {

	@Autowired
	private MemberNicknameService memberNicknameService;
	
	
	@Override
	public Result updateMemberNickName(Long id, String nickname) {
		// TODO Auto-generated method stub
		return memberNicknameService.updateMemberNickName(id,nickname);
	}

}
