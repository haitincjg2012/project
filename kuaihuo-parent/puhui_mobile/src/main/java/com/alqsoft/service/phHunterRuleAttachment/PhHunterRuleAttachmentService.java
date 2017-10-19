package com.alqsoft.service.phHunterRuleAttachment;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;

public interface PhHunterRuleAttachmentService {
	// 上传图片
	public Result mobileUploadAttachment(MultipartFile urlfile, int type, Object[] backUrl, String module,
			String[] extendFile);
	//保存图片
	public Result saveAttachment(PhHunterRuleAttachment phHunterRuleAttachment);
	//上传图片关联hunterid
	public Result addAttachment(Long hunterid,Long imageid);
}
