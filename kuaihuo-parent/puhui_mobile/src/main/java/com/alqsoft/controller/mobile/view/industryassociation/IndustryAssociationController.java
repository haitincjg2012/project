package com.alqsoft.controller.mobile.view.industryassociation;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.industryassociation.IndustryAssociationService;

/**
 * @ClassName  IndustryAssociationController
 * Date:     2017年3月1日  9:25:41 <br/>
 * @author   dinglanlan
 * @version  行业协会
 * @see 	 
 */
@RequestMapping("mobile/view/industryassociation")
@Controller
public class IndustryAssociationController {

	@Autowired
	private IndustryAssociationService industryAssociationService;
	
	/**
	 * 所属协会列表接口
	 * @return
	 */
	@RequestMapping(value="getindustryassociation",method=RequestMethod.POST)
	@ResponseBody
	public Result getindustryassociation(){
		
		Result result =this.industryAssociationService.findIndustryAssociationList();
		
		return result;
	}
	
	
	
}
