package com.alqsoft.controller.mobile.after.member;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.model.HunterModel;
import com.alqsoft.service.member.MemberService;

@RequestMapping("mobile/after/member")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 用户个人中心
	 * @return
	 */
	@RequestMapping(value="membercenter",method=RequestMethod.POST)
	@ResponseBody
	public Result memberCenter(@MemberAnno Member member){
		
		return memberService.getMemberCenter(member.getId());

	}
	/**
	 * 通过IMid返回用户的昵称
	 * @author wudi
	 * @param imId 腾讯Id
	 * @return
	 */
	@RequestMapping(value="nickname",method=RequestMethod.POST)
	@ResponseBody
	public Result nickNameByImid(@MemberAnno Member member,@RequestParam(value="imId") String imId){
		return  memberService.getNickNameByImid(member,imId);
	
	}
}
