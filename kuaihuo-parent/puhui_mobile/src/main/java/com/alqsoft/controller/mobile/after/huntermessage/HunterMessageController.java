package com.alqsoft.controller.mobile.after.huntermessage;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntermessage.MessageService;

/**
 * 4、获取批发商信息查询
 * 
 * @author wangzn
 *
 */
@RestController

@RequestMapping("mobile/after/huntermessage")
public class HunterMessageController {

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "huntermessage-list", method = RequestMethod.POST)
	@ResponseBody
	public Result huntermessage(@MemberAnno Member member) {
		Result rs = messageService.HunterMessageList(member);
		return rs;
	}

}
