package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;

public interface RpcHunterSourcingService {

	public Result saveOrModifySourcingService(String detail, Member member);

}
