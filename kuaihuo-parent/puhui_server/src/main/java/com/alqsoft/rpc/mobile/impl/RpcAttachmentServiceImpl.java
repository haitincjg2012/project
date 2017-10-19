package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.rpc.mobile.RpcAttachmentService;
import com.alqsoft.service.attachment.AttachmentService;

@Controller
public class RpcAttachmentServiceImpl implements RpcAttachmentService {
	
	@Autowired
	private AttachmentService attachmentService;

	

	/**
	 * 上传图片
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param module
	 * @return
	 */
	@Override
	public Attachment mobileUploadAttachment(Attachment attachment) {
		// TODO Auto-generated method stub
		return attachmentService.saveAndModify(attachment);
	}

}
