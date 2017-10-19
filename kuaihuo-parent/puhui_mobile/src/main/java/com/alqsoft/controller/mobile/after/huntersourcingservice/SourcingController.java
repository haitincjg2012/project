package com.alqsoft.controller.mobile.after.huntersourcingservice;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntersourcingservice.SourcingService;

/**
 * 批发商货源服务--列表+添加
 * 
 * @author wangzn
 *
 */
@RequestMapping("mobile/after/huntersourcingservice")
@Controller
public class SourcingController {
	@Autowired
	private SourcingService sourcingService;

	/**
	 * 批发商货源服务--列表
	 * 
	 * @param id
	 * @param page
	 * @param rows
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "huntersourcingservice-list", method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterServiceRuleList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "5") Integer rows, @MemberAnno Member member) {

		Result result = this.sourcingService.getSourcingServiceList(page, rows, member);

		return result;

	}

	/**
	 * 批发商货源服务--添加
	 * 
	 * @param hs
	 * @param member
	 * @return
	 */
	@Explosionproof // 在需要防爆的方法上加上注解@Explosionproof
	@RequestMapping(value = "add-huntersourcingservice", method = RequestMethod.POST)
	@ResponseBody
	public Result saveOrModifySourcingService(String detail, @MemberAnno Member member) {
		Result result = this.sourcingService.saveOrModifySourcingService(detail, member);
		return result;

	}

}
