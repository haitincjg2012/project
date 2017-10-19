package com.alqsoft.controller.mobile.view.hunterrule;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterrule.HunterRuleService;


@RestController

/**
 *3、批发商法则列表查询&首页展示不需要登录
 * 
 * @return
 */
@RequestMapping("mobile/view/hunterrule")
public class HunterRuleViewController {
	
	@Autowired
	private  HunterRuleService hunterRuleService;
	
	@RequestMapping(value="hunterrule-list",method=RequestMethod.POST)
	@ResponseBody
	public Result HunterRuleList(Long hunter_id,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="rows",defaultValue="10")Integer rows
			) {
		Member member=new Member();
		Hunter hunter=new Hunter();
		hunter.setId(hunter_id);
		member.setHunter(hunter);
		Result rs = hunterRuleService.getRoleList(member,page,rows);
		return rs;
	}
	
	

}
