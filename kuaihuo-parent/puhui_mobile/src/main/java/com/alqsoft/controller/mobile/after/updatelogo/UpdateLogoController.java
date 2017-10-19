package com.alqsoft.controller.mobile.after.updatelogo;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.updatelogo.UpdateLogoService;

/**
 * 批发商logo修改+会员logo修改+批发商服务修改+专业修改
 * 
 * @author wangzn
 *
 */
@Controller
@RequestMapping("mobile/after/updatelogo")
public class UpdateLogoController {

	@Autowired
	private UpdateLogoService updateLogoService;

	/**
	 * 批发商LOGO修改
	 * 
	 * @param attachmentId
	 * @param member
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-hunter-logo", method = RequestMethod.POST)
	@ResponseBody
	public Result updatelogo(Long attachmentId, @MemberAnno Member member) {
		try {
			Result result = this.updateLogoService.updateLogo(attachmentId, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商LOGO修改异常");
		}
	}

	/**
	 * 会员logo修改
	 * 
	 * @param attachmentId
	 * @param member
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-member-logo", method = RequestMethod.POST)
	@ResponseBody
	public Result updatememberlogo(Long attachmentId, @MemberAnno Member member) {
		try {
			Result result = this.updateLogoService.updatememberlogo(attachmentId, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("会员LOGO修改异常");
		}
	}

	/**
	 * 批发商服务修改
	 * 
	 * @param id
	 * @param service
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-business", method = RequestMethod.POST)
	@ResponseBody
	public Result updatebusiness(String service, @MemberAnno Member member) {
		try {
			Result result = this.updateLogoService.updatebusiness(service, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商服务修改异常");
		}
	}

	/**
	 * 批发商专业修改
	 * 
	 * @param id
	 * @param service
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-major", method = RequestMethod.POST)
	@ResponseBody
	public Result updatemajor(String major, @MemberAnno Member member) {
		try {
			Result result = this.updateLogoService.updatemajor(major, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商专业修改异常");
		}
	}

	/**
	 * 批发商服务内容修改
	 * 
	 * @param id
	 * @param servicedigest
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-servicedigest", method = RequestMethod.POST)
	@ResponseBody
	public Result updateservicedigest(String servicedigest, @MemberAnno Member member) {
		try {
			Result result = this.updateLogoService.updateservicedigest(servicedigest, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("批发商服务内容修改异常");
		}
	}

}
