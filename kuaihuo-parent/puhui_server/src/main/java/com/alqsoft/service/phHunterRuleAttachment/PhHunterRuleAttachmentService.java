package com.alqsoft.service.phHunterRuleAttachment;

import java.util.List;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;

public interface PhHunterRuleAttachmentService extends BaseService<PhHunterRuleAttachment>{
	// 上传图片
	public Result mobileUploadAttachment(MultipartFile urlfile, int type, Object[] backUrl, String module,
			String[] extendFile);
	//保存图片
	public Result saveAttachment(PhHunterRuleAttachment phHunterRuleAttachment);
	
	public List<Long> ruleAttachmentId(Long hunterid,int type);
	//更新图片
	public Result  updateAttachment(Long saveimageid,Long updateimageid,Long hunterid,int type);
	
	//关联图图片
	public Result addAttachment(Long hunterid, Long imageid);
	
}
