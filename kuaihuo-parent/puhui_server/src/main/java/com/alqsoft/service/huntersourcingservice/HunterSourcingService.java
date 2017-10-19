package com.alqsoft.service.huntersourcingservice;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;

public interface HunterSourcingService extends BaseService<HunterService>{

	public Result saveOrModifySourcingService( String detail, Member member);

}
