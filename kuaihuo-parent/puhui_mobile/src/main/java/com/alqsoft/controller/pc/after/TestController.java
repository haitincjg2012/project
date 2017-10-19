package com.alqsoft.controller.pc.after;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;

@RequestMapping("pc/after/test")
@Controller
public class TestController {
	@RequestMapping("test")
	@ResponseBody
	public Result getResult(Member m,@MemberAnno Member member){
		
		return ResultUtils.returnSuccess("dsdsd",member);
	}

}
