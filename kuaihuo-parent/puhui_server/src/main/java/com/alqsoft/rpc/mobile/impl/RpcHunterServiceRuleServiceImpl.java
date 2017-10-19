package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterServiceRuleService;
import com.alqsoft.service.hunterruleattachment.HunterRuleAttachmentService;
import com.alqsoft.service.hunterservicerule.HunterServiceRuleService;
@Service
@Transactional
public class RpcHunterServiceRuleServiceImpl implements RpcHunterServiceRuleService{

	
	@Autowired
	private HunterServiceRuleService hunterServiceRuleService;
	
	@Autowired
	private HunterRuleAttachmentService hunterRuleAttachmentService;
	
	@Override
	public Result delServiceruleById(Long id, Member member) {
		return hunterServiceRuleService.delServiceruleById(id,member);
	}
	@Override
	public Result updateServicerule(Long id, String attachmentids, String content, Member member) {
		return hunterServiceRuleService.updateServicerule(id,attachmentids,content,member);
	}
	@Override
	public HunterRuleAttachment mobileUploadhunterRuleAttachment(HunterRuleAttachment hunterRuleAttachment) {
		// TODO Auto-generated method stub
		return hunterRuleAttachmentService.saveAndModify(hunterRuleAttachment);
	}

}
