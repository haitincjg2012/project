package com.alqsoft.controller.push;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.alqsoft.entity.push.Push;
import com.alqsoft.service.push.PushService;

/**
 * 
 * @Title: PushController.java
 * @Description: 百度消息推送
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月14日 下午4:36:07
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("push")
@Controller
public class PushController {
	
	@Autowired
	private PushService pushService;
	/**
	 * 跳转到百度推送视图
	 * @Title: pushList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("push-list")
	public String pushList(Model model){
		
		return "push/push-list";
	}
	
	/**
	 * 获取百度推送分页信息
	 * @Title: getPushPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("push-list-data")
	@ResponseBody
	public EasyuiResult<List<Push>> getPushPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return pushService.getPushPage(map, page, rows);
	}
	/**
	 * 百度推送删除
	 * @Title: pushDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("push-delete")
	@ResponseBody
	public Result pushDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					pushService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除百度推送成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除百度推送失败");
		}
		
	}
}
