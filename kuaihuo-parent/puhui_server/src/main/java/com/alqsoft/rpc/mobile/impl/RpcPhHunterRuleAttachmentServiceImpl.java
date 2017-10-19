package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;
import com.alqsoft.rpc.mobile.RpcPhHunterRuleAttachmentService;
import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;
@Service
@Transactional
public class RpcPhHunterRuleAttachmentServiceImpl implements RpcPhHunterRuleAttachmentService {

	@Autowired
	private PhHunterRuleAttachmentService phHunterRuleAttachmentService;
	
	@Override
	public PhHunterRuleAttachment mobileUploadAttachment(PhHunterRuleAttachment phHunterRuleAttachment) {
		// TODO Auto-generated method stub
		return phHunterRuleAttachmentService.saveAndModify(phHunterRuleAttachment);
	}

	@Override
	public Result updateAttachment(Long saveimageid,Long updateimageid,Long hunterid,int type) {
		
		return phHunterRuleAttachmentService.updateAttachment(saveimageid,updateimageid, hunterid, type);
	}

	@Override
	public Result addAttachment(Long hunterid, Long imageid) {
		return phHunterRuleAttachmentService.addAttachment(hunterid, imageid);
	}
	
}
