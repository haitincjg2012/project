package com.alqsoft.controller.feedback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.collection.MyCollectionUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.feedback.FeedBack;
import com.alqsoft.entity.feedback.FeedBackType;
import com.alqsoft.service.feedback.FeedBackService;
import com.alqsoft.service.feedback.FeedBackTypeService;

/**
 * 
 * @Title: FeedBackController.java
 * @Description: 意见反馈控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:41:12
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("feedback")
@Controller
public class FeedBackController {
	@Autowired
	private FeedBackService feedBackService;
	
	@Autowired
	private FeedBackTypeService feedBackTypeService;
	/**
	 * 跳转到意见反馈视图
	 * @Title: feedbackList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("feedback-list")
	public String feedbackList(Model model){
		List<FeedBackType> feedBackTypeList = feedBackTypeService.findFeedBackTypeAll();
		if(MyCollectionUtils.isNotEmpty(feedBackTypeList))
		{
			model.addAttribute("feedBackTypeList", feedBackTypeList);
		}
		return "feedback/feedback-list";
	}
	
	/**
	 * 获取意见反馈分页信息
	 * @Title: getAttentionPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("feedback-list-data")
	@ResponseBody
	public EasyuiResult<List<FeedBack>> getAttentionPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return feedBackService.getFeedBackPage(map, page, rows);
	}
	/**
	 * 意见反馈删除
	 * @Title: feedbackDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("feedback-delete")
	@ResponseBody
	public Result feedbackDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					feedBackService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除反馈意见成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除反馈意见失败");
		}
		
	}
	/**
	 * 意见反馈查看
	 * @Title: searchfeedback
	 * @Description: TODO
	 * @param: @param id
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("feedback-search")
	public String searchfeedback(@RequestParam(value="id",required=false) Long id,Model model){
		FeedBack feedback=null;
		if(id!=null){
			feedback=feedBackService.get(id);
			if(feedback.getId()!=null){
				model.addAttribute("feedback", feedback);
			}
		}
		return "feedback/feedback-search";
	}
	/**
	 * 改变意见反馈状态
	 * @Title: changefeedbackState
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("feedback-changeState")
	@ResponseBody
	public Result changefeedbackState(@RequestParam(value="id",required=false) Long id){
		FeedBack feedback=feedBackService.get(id);
		if(feedback.getIsLook()==0){
			feedback.setIsLook(1);
			try
			{
				feedBackService.saveAndModify(feedback);
				return SpringMVCUtils.returnSuccess("意见反馈更改成功");
			}catch(Exception e)
			{
				return SpringMVCUtils.returnError("操作失败");
			}
		}else{
			return SpringMVCUtils.returnError("该意见反馈已经查看过");
		}
	}
}
