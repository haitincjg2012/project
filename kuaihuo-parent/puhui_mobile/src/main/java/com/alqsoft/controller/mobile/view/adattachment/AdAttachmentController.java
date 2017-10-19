package com.alqsoft.controller.mobile.view.adattachment;
/**
 * 
 * @Description: 首页数据的获取,广告滚动展示
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年2月28日 下午3:28:57
 * Copyright 
 */


import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.indexadattach.AdAttachmentService;

@RequestMapping("mobile/view/adattachment")
@Controller
public class  AdAttachmentController {
	
	
	@Autowired
	private AdAttachmentService indexAdAttachService;
	
	@ResponseBody
	@RequestMapping(value="findalladattachment" ,method=RequestMethod.POST)
	public Result findAllAdAttachment(@RequestParam(value="startInt" ,defaultValue="0") Integer startInt,@RequestParam(value="endInt" ,defaultValue="4") Integer endInt){
		
		Result result =  indexAdAttachService.findAll(startInt,endInt);
		
		return result;
	}
	/***
	 * 点击轮播图进入详情页面
	 * @param id 轮播图对应的图片id
	 * @return
	 */
	@RequestMapping(value="textdetail" )
	public String getTextDetail(Long id,Model model){
		indexAdAttachService.getTextDetail(id,model);
		return "view/textDetail";
	}

}
