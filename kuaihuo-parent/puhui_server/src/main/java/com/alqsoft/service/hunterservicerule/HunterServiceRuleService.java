package com.alqsoft.service.hunterservicerule;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.hunterrule.HunterRule;
import com.alqsoft.entity.member.Member;

public interface HunterServiceRuleService extends BaseService<HunterRule>{

	public Result delServiceruleById(Long id, Member member);

	public Result updateServicerule(Long id, String attachmentids, String content, Member member);


}
