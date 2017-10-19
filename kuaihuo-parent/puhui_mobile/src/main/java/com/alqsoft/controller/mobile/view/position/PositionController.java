package com.alqsoft.controller.mobile.view.position;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.position.PositionService;

@RequestMapping("mobile/view/position")
@Controller
public class PositionController {

	@Autowired
	private PositionService positionService; 
	
	/**
	 * 省市县区
	 * @param id
	 * @param type 0省1市2县3区
	 * @return
	 */
	@RequestMapping(value="getpositionbyidandtype", method=RequestMethod.POST)
	@ResponseBody
	public Result getPositionByIdAndType(String id, int type){
		
		return this.positionService.getPositionByIdAndType(id, type);
	}
	
	/**
	 * 根据区编号获取数据信息
	 * @param id
	 * @return
	 *//*
	@RequestMapping("getPositionByTownId")
	@ResponseBody
	public Position getPositionByTownId(String id){
		return this.positionService.getPositionByTownId(Long.valueOf(id));
	}
	
	*//**
	 * 根据县编号获取市信息
	 * @param id
	 * @return
	 *//*
	@RequestMapping("getCityInfoByCountyId")
	@ResponseBody
	public Position getCityInfoByCountyId(String id){
		return this.positionService.getCityInfoByCountyId(Long.valueOf(id));
	}*/
	
}
