package com.alqsoft.controller.mobile.view.area;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.service.area.AreaService;

@RestController
@RequestMapping("mobile/view/area")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	/**
	 * 获取省的集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="findprolist",method=RequestMethod.POST)
	public Result findProList(){
		return this.areaService.findProList();
	}
	
	/**
	 * 根据省的id获取市的集合
	 * @param pId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="findcitylistbypid",method=RequestMethod.POST)
	public Result findCityListByPId(Long pId){
		return this.areaService.findCityListByPId(pId);
	}
	
	/**
	 * 获取省市的json字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="findarealist",method=RequestMethod.POST)
	public Result findAreaList(){
		return this.areaService.findAreaList();
	}
	
}
