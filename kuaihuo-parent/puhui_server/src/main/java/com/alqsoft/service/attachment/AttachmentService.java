package com.alqsoft.service.attachment;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;

public interface AttachmentService extends BaseService<Attachment>{

	public Result uploadAttachment(MultipartFile urlfile, Object[] backUrl,String module,String[]extendFile);

	public Result saveAttachment(Attachment attachment);
}
