package com.alqsoft.controller.pc.after;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.adshomedata.AdsHomeDataService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月20日 上午12:12:07
 * Copyright 
 */
@RequestMapping("pc/after/adshomedata")
@Controller
public class AdsHomeDataController {
	@Autowired
	private AdsHomeDataService adsHomeDataService;
	@RequestMapping("ads-list-data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> typelist(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		Map<String, Object> params = ServletUtils.getParametersStartingWith(request, "search_");
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>> typelist= adsHomeDataService.findIndustryTypeListMybatis(params);
		int count = adsHomeDataService.getIndustryTypeListCount(params);
		EasyuiResult easyuiResult = new EasyuiResult();
		easyuiResult.setT(typelist);
		easyuiResult.setTotal((long) count);
		return easyuiResult;
	}
	
}
