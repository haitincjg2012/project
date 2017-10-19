package com.alqsoft.controller.pc.after;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.utils.UploadFileName;


@RequestMapping("pc/after/attachment")
@Controller
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * 上传图片
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("uload-attachment")
	@ResponseBody
	public Result uloadattachment(
			@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request
			){
		
		String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module=UploadFileName.HUNTER_INDUSTRY.getName();
		
		return attachmentService.uploadAttachment(urlfile,new Object[]{attachmentService,"saveAttachment"},module,extendFile);
	}
}
