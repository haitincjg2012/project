package com.alqsoft.controller.mobile.after.hunterrule;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterrule.HunterRuleService;

@RestController

/**
 * 3、批发商法则列表查询
 * 
 * @return
 */
@RequestMapping("mobile/after/hunterrule")
public class HunterRuleController {

	@Autowired
	private HunterRuleService hunterRuleService;

	@RequestMapping(value = "hunterrule-list", method = RequestMethod.POST)
	@ResponseBody
	public Result HunterRuleList(@MemberAnno Member member,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows) {
		Result rs = hunterRuleService.getRoleList(member, page, rows);
		return rs;
	}

}
