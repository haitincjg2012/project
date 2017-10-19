package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterSourcingService;
import com.alqsoft.service.hunterservicerule.HunterServiceRuleService;
import com.alqsoft.service.huntersourcingservice.HunterSourcingService;

@Service
@Transactional
public class RpcHunterSourcingServiceImpl implements RpcHunterSourcingService{
	
	@Autowired
	private HunterSourcingService hunterSourcingService;

	@Override
	public Result saveOrModifySourcingService(String detail, Member member) {
		// TODO Auto-generated method stub
		return hunterSourcingService.saveOrModifySourcingService(detail,member);
	}

}
