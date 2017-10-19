package com.alqsoft.controller.mobile.view.attachment;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.utils.UploadFileName;

/**
 * @ClassName  IndustryAssociationController
 * Date:     2017年3月2日  10:45:41 <br/>
 * @author   dinglanlan
 * @version  批发商上传图片
 * @see 	 
 */
@RequestMapping("mobile/view/attachment")
@Controller
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * APP 上传图片头像接口
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("mobileUpload-salecircleattachment")
	@ResponseBody
	public Result mobileUploadSaleCircleAttachment(
			@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request
			){
		
		String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module=UploadFileName.HUNTER_HEADIMG.getName();
		
		return attachmentService.mobileUploadAttachment(urlfile,new Object[]{attachmentService,"saveAttachment"},module,extendFile);
	}
}
