package com.alqsoft.service.huntersourcingservice;

import org.alqframework.result.Result;

import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;

public interface SourcingService {

	public Result getSourcingServiceList( Integer page, Integer rows, Member member);

	public Result saveOrModifySourcingService(String detail, Member member);

}
