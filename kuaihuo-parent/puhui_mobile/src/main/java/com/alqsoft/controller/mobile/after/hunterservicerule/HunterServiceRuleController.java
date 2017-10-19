package com.alqsoft.controller.mobile.after.hunterservicerule;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.hunterservicerule.HunterServiceRuleService;
import com.alqsoft.utils.UploadFileName;

/**
 * 批发商服务法则列表获取+添加+删除+编辑
 * 
 * @author wangzn
 *
 */
@RequestMapping("mobile/after/hunterservicerule")
@Controller
public class HunterServiceRuleController {

	@Autowired
	private HunterServiceRuleService hunterServiceRuleService;
	
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 批发商服务法则列表获取-编辑时的查询
	 * 
	 * @param id
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "servicerule-list", method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterServiceRuleList(@RequestParam(value = "id") Long id, @MemberAnno Member member) {

		Result result = this.hunterServiceRuleService.getHunterServiceRuleList(id, member);
		return result;
	}

	/**
	 * 批发商法则删除
	 * 
	 * @param id
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "del-servicerule", method = RequestMethod.POST)
	@ResponseBody
	public Result delServiceruleById(Long id, @MemberAnno Member member) {
		try {
			Result result = this.hunterServiceRuleService.delServiceruleById(id, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商服务法则删除异常");
		}
	}

	/**
	 * 批发商法则编辑
	 * 
	 * @param 法则id
	 * @param member
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-servicerule", method = RequestMethod.POST)
	@ResponseBody
	public Result updateServicerule(@RequestParam(required = false, defaultValue = "0") Long id, String attachmentids,
			String content, @MemberAnno Member member) {
		/*try {*/
			Result result = this.hunterServiceRuleService.updateServicerule(id, attachmentids, content, member);
			return result;
		/*} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商服务法则编辑异常");
		}*/
	}

	/**
	 * 上传法则图片的接口
	 * 
	 * @param urlfile
	 * @param field
	 * @param ext
	 * @param request
	 * @return
	 */
	@RequestMapping("mobileUpload-uploadhunterruleattachment")
	@ResponseBody
	public Result mobileUploadSaleCircleAttachment(@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field, HttpServletRequest request) {

		String[] extendFile = new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
		String module = UploadFileName.HUNTER_RULE.getName();

		return hunterServiceRuleService.mobileUploadAttachment(urlfile,
				new Object[] { attachmentService, "saveHunterRuleAttachment" }, module, extendFile);
	}

}
