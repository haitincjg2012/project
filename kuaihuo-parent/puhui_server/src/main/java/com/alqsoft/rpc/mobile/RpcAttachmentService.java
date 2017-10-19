package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;

public interface RpcAttachmentService {

	/**
	 * 上传图片
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param module
	 * @return
	 */
	public Attachment mobileUploadAttachment(Attachment attachment);

}
