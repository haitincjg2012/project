package com.alqsoft.controller.mobile.after.hunternickname;

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
import com.alqsoft.service.hunternickname.HunterNicknameService;

/**
 * 6、批发商昵称修改+会员昵称修改
 * 
 * @author wangzn
 *
 */
@RequestMapping("mobile/after/hunternickname")
@Controller
public class HunterNicknameController {

	@Autowired
	private HunterNicknameService hunterNicknameService;

	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "update-nickname", method = RequestMethod.POST)
	@ResponseBody
	public Result updateNickname(Integer type, String nickname, @MemberAnno Member member) {
		try {
			Result result = this.hunterNicknameService.updateNickname(type, nickname, member);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("昵称修改异常");
		}
	}

}
