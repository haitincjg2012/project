package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.rpc.mobile.RpcHunterNickNameService;
import com.alqsoft.service.hunternickname.HunterNicknameService;
@Service
@Transactional
public class RpcHunterNickNameServiceImpl implements RpcHunterNickNameService{
	
	@Autowired
	private HunterNicknameService hunterNicknameService;
	@Override
	public Result updateHunterNickName(Hunter hunter) {
		// TODO Auto-generated method stub
		return this.hunterNicknameService.updateHunterNickName(hunter);
	}

	

}
