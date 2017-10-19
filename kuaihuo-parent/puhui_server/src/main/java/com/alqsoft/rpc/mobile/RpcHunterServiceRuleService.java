package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;
import com.alqsoft.entity.member.Member;

public interface RpcHunterServiceRuleService {
	
	
	public Result delServiceruleById(Long id, Member member);
	
	public Result updateServicerule(Long id, String attachmentids, String content, Member member);
	
	public HunterRuleAttachment mobileUploadhunterRuleAttachment(HunterRuleAttachment hunterRuleAttachment);
}
