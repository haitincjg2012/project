package com.alqsoft.service.hunterruleattachment;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;

public interface HunterRuleAttachmentService extends BaseService<HunterRuleAttachment>{

	public  List<HunterRuleAttachment> getHunterRuleAttachmentByHunterRuleId(Long hunterRuleId);
	
	
	public void updateHunterRulePicture(Collection<Long> pids,Long ruleid);
}
