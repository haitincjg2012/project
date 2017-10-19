package com.alqsoft.controller.feedback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.alqframework.bean.MyBeanUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.feedback.FeedBackType;
import com.alqsoft.service.feedback.FeedBackService;
import com.alqsoft.service.feedback.FeedBackTypeService;

/**
 * 
 * @Title: FeedBackTypeController.java
 * @Description: 意见反馈类型控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:41:12
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("feedbacktype")
@Controller
public class FeedBackTypeController {
	@Autowired
	private FeedBackTypeService feedBackTypeService;
	
	@Autowired
	private FeedBackService feedBackService;
	private boolean flag = false;
	/**
	 * 跳转到意见反馈类型视图页面
	 * @Title: feedBackTypeList
	 * @Description: TODO
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("feedbacktype-list")
	public String feedBackTypeList() {
		return "feedbacktype/feedbacktype-list";
	}
	
	/**
	 * 意见反馈类型分页内容
	 * @Title: feedBackTypeListData
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBackType>>
	 * @throws
	 */
	@RequestMapping("feedbacktype-list-data")
	@ResponseBody
	public EasyuiResult<List<FeedBackType>> feedBackTypeListData(
			@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return feedBackTypeService.getFeedBackTypePage(map, page, rows);
	}
	/**
	 * 意见反馈类型新增或修改
	 * @Title: feedbacktypeInput
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param id
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("feedbacktype-input")
	public String feedbacktypeInput(Model model,
			@RequestParam(value = "id", required = false) Long id) {
		FeedBackType type = null;
		if (id != null) {
			type = feedBackTypeService.get(id);
			if (type != null) {
				model.addAttribute("feedbacktype", type);
			}
			return "feedbacktype/feedbacktype-input";
		} else {
			type = new FeedBackType();
			model.addAttribute("feedbacktype", type);
			return "feedbacktype/feedbacktype-input";
		}

	}
	/**
	 * 意见反馈类型增加或修改操作
	 * @Title: feedbacktypeAdd
	 * @Description: TODO
	 * @param: @param type
	 * @param: @param result
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("feedbacktype-add")
	@ResponseBody
	public Result feedbacktypeAdd(@Valid FeedBackType type, BindingResult result) {
		if (!result.hasErrors()) {
			if (type.getId() == null) {
				if (feedBackTypeService.getFeedBackTypeByTypeName(type
						.getTypeName()) != null) {
					return SpringMVCUtils.returnError("意见反馈类型名重复，请重新输入");
				}
				try
				{
					feedBackTypeService.saveAndModify(type);
					return SpringMVCUtils.returnSuccess("意见反馈类型新增成功");
				}catch(Exception e)
				{
					return SpringMVCUtils.returnError("系统错误，请联系管理员");
				}
			} else {
				FeedBackType type2 = feedBackTypeService.get(type.getId());
				MyBeanUtils.propertyUtils(type2, type);
				try
				{
					feedBackTypeService.saveAndModify(type2);
					return SpringMVCUtils.returnSuccess("意见反馈类型修改成功");
				}catch(Exception e)
				{
					return SpringMVCUtils.returnError("系统错误，请联系管理员");
				}
			}
		} else {
			return SpringMVCUtils.returnError(result.getAllErrors().get(0)
					.toString());
		}

	}
	
	/**
	 * 意见反馈类型删除
	 * @Title: feeBackTypeDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("feedbacktype-delete")
	@ResponseBody
	public Result feedBackTypeDelete(@RequestParam("id") String id) {
		flag =false;
		String[] ids = StringUtils.split(id, ",");
		try
		{
			Arrays.stream(ids).forEach(s->{
				Long num = feedBackService.getFeedBackNumByTypeId(Long.parseLong(s));
				if(num>0)
				{
					flag = true;
					return;
				}
				else
				{
					feedBackTypeService.delete(Long.parseLong(s));
				}
			});
		if(flag)
		{
			return SpringMVCUtils.returnError("请先删除意见反馈类型下的意见反馈信息");
		}
		return SpringMVCUtils.returnSuccess("意见反馈信息删除成功");
		}catch(Exception e)
		{
			return SpringMVCUtils.returnError("删除意见反馈类型失败");
		}
		
	}
}
