package com.alqsoft.controller.mobile.view.phHunterRuleAttachment;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;
import com.alqsoft.utils.UploadFileName;

/**
 * @ClassName  IndustryAssociationController
 * Date:     2017年3月2日  10:45:41 <br/>
 * @author   dinglanlan
 * @version  批发商上传营业执照、身份证图片
 * @see 	 
 */
@RequestMapping("mobile/view/phHunterRuleAttachment")
@Controller
public class PhHunterRuleAttachmentController {
	@Autowired
	private PhHunterRuleAttachmentService phHunterRuleAttachmentService;
	
	/**
	 * 批发商上传营业执照、身份证图片接口
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("mobileUpload-phHunterRuleAttachment")
	@ResponseBody
	public Result mobileUploadSaleCircleAttachment(
			@RequestParam("urlfile") MultipartFile urlfile,@RequestParam("type") int type,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request
			){
		
		String[] extendFile=new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module=UploadFileName.HUNTER_HEADIMG.getName();
		
		return phHunterRuleAttachmentService.mobileUploadAttachment(urlfile,type,new Object[]{phHunterRuleAttachmentService,"saveAttachment"},module,extendFile);
	}
	
	/**
	 * 批发商上传营业执照、身份证图片接口
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("addAttachment")
	@ResponseBody
	public Result addAttachment(
			@RequestParam("imageid") Long imageid,@RequestParam("hunterid") Long hunterid){
		return phHunterRuleAttachmentService.addAttachment(hunterid, imageid);
				
	}
	
	
}
