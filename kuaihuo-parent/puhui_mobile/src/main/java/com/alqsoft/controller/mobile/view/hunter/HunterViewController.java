package com.alqsoft.controller.mobile.view.hunter;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.model.HunterLabelModel;
import com.alqsoft.model.HunterModel;
import com.alqsoft.service.hunter.HunterService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("mobile/view/hunter")
@Controller
public class HunterViewController {

	
	@Autowired
	private HunterService hunterService;
	
	
	/**
	 * 注册-接口
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="addhunterandregister",method=RequestMethod.POST)
	@ResponseBody
	public Result addHunterAndRegister(HunterModel hunterModel){
		
		
		
		return this.hunterService.addHunterAndRegister(hunterModel);
	}
	
	
	/**
	 * 注册-完善批发商信息接口
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="addhunter",method=RequestMethod.POST)
	@ResponseBody
	public Result addhunter(HunterModel hunterModel){
		
		
		
		return this.hunterService.addHunter(hunterModel);
	}

	/**
	 * 根据专题分类获取所有批发商信息
	 * @param sid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "getHuntersBySubject",method = RequestMethod.POST)
	public @ResponseBody Result getHuntersBySubject(@RequestParam("sid")Long sid,
													@RequestParam(value = "page",defaultValue ="1",required = false)int page,
													@RequestParam(value = "rows",defaultValue ="10",required = false)int size){

		return hunterService.getHuntersBySubjectId(sid,page,size);
	}
	
	/**
	 * 根据行业分类查询批发商列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="hunterbyindustry",method=RequestMethod.POST)
	@ResponseBody
	public Result findHunterByIndustryId(
			@RequestParam(value="id") Long id,
			@RequestParam(value="page",defaultValue="1",required=false)Integer page,
			@RequestParam(value="rows",defaultValue="10",required=false)Integer rows){
		return hunterService.findHunterByIndustryTypeId(id,page,rows);
	}
	
	/**
	 * 注册-完善批发商信息一期优化接口
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="addhunterlabel",method=RequestMethod.POST)
	@ResponseBody
	public Result addhunterLabel(HunterLabelModel hunterLabelModel){
		
		
		
		return this.hunterService.addhunterLabel(hunterLabelModel);
	}
}
