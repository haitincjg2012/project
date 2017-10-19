package com.alqsoft.service.attachment;

import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;

public interface AttachmentService {

	/**
	 * 上传图片
	 * @param urlfile
	 * @param type 
	 * @return
	 */
	public Result mobileUploadAttachment(MultipartFile urlfile, Object[]backUrl,String module,String[]extendFile);


	public Result saveAttachment(Attachment attachment);
	
	/**
	 * 保存商品轮播图片
	 * @param productAttachment
	 * @return
	 */
	public Result saveProductAttachment(Attachment attachment);
	
	/**
	 * 
	 * @param attachment
	 * @return
	 */
	public Result saveHunterRuleAttachment(Attachment attachment);
	

}
