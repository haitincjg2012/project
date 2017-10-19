package com.alqsoft.controller.push;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.push.PushLog;
import com.alqsoft.service.push.PushLogService;


/**
 * 
 * @Title: PushLogController.java
 * @Description: 百度消息推送日志
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月14日 下午4:36:07
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("pushlog")
@Controller
public class PushLogController {
	
	@Autowired
	private PushLogService pushLogService;
	/**
	 * 跳转到百度推送日志视图
	 * @Title: pushlogList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("pushlog-list")
	public String pushLogList(Model model){
		
		return "push/pushlog-list";
	}
	
	/**
	 * 获取百度推送日志分页信息
	 * @Title: getPushLogPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<PushLog>>
	 * @throws
	 */
	@RequestMapping("pushlog-list-data")
	@ResponseBody
	public EasyuiResult<List<PushLog>> getPushLogPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return pushLogService.getPushLogPage(map, page, rows);
	}
}
	/**
	 * 百度推送日志删除
	 * @Title: pushlogDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	/*@RequestMapping("pushlog-delete")
	@ResponseBody
	public Result pushLogDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					pushLogService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除百度推送日志成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除百度推送日志失败");
		}
		
	}*/


