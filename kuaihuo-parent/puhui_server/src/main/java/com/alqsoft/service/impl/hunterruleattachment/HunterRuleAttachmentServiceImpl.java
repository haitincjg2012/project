package com.alqsoft.service.impl.hunterruleattachment;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterruleattachment.HunterRuleAttachmentDao;
import com.alqsoft.entity.hunterruleattachment.HunterRuleAttachment;
import com.alqsoft.service.hunterruleattachment.HunterRuleAttachmentService;

@Service
@Transactional
public class HunterRuleAttachmentServiceImpl implements HunterRuleAttachmentService{
	
	@Autowired
	private HunterRuleAttachmentDao hunterRuleAttachmentDao;

	@Override
	public List<HunterRuleAttachment> getHunterRuleAttachmentByHunterRuleId(Long hunterRuleId) {
		// TODO Auto-generated method stub
		List<HunterRuleAttachment> attachments=	this.hunterRuleAttachmentDao.getHunterRuleAttachmentByHunterRuleId(hunterRuleId);
		return attachments;
	}

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HunterRuleAttachment get(Long arg0) {
		// TODO Auto-generated method stub
		return hunterRuleAttachmentDao.findOne(arg0);
	}

	@Override
	public HunterRuleAttachment saveAndModify(HunterRuleAttachment arg0) {
		// TODO Auto-generated method stub
		return hunterRuleAttachmentDao.save(arg0);
	}



	@Override
	public void updateHunterRulePicture(Collection<Long> pids, Long ruleid) {
		// TODO Auto-generated method stub
		hunterRuleAttachmentDao.updateHunterRulePicture(pids, ruleid);
		
	}
 
}
