package com.alqsoft.controller.mobile.view.hunterall;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.hunterall.HunterAllService;

/**
 * 1、热门推荐全部批发商查询
 * 
 * @author wangzn
 *
 */
@RequestMapping("mobile/view/hunterall")
@Controller
public class HunterAllController {

	@Autowired
	private HunterAllService hunterAllService;

	// 一期的路径
	@RequestMapping(value = "hunterall-list", method = RequestMethod.POST)
	@ResponseBody
	public Result hunter() {
		Result rs = hunterAllService.getHunterAllList();
		return rs;
	}

	// 二期的路径
	@RequestMapping(value = "hunterall-list2", method = RequestMethod.POST)
	@ResponseBody
	public Result hunter2() {
		Result rs = hunterAllService.getHunterAllList2();
		return rs;
	}

	/***
	 * 获取所有的批发商，通过距离排序
	 * 
	 * @param longitude  经度
	 * @param latitude 纬度
	 * @return
	 * @author wudi
	 * @version 1.2
	 * @date 2017/5/22
	 */
	@RequestMapping(value = "hunterall_gitude", method = RequestMethod.POST)
	@ResponseBody
	public Result getAllHunter(@RequestParam(value = "longitude", defaultValue = "lo") String longitude,
			@RequestParam(value = "latitude", defaultValue = "la") String latitude,
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "numPage", defaultValue = "10") Integer numPage) {
		Result allHunter = hunterAllService.getAllHunter(longitude, latitude, currentPage, numPage);
		return allHunter;
	}



}
