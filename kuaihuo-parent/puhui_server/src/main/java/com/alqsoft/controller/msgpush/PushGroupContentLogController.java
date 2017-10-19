package com.alqsoft.controller.msgpush;

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

import com.alqsoft.entity.msgpush.PushGroupContentLog;
import com.alqsoft.service.msgpush.PushGroupContentLogService;

/**
 * 
 * @Title: MsgPushController.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月20日 上午9:59:28
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("pushgroupcontentlog")
@Controller
public class PushGroupContentLogController {
	@Autowired
	private PushGroupContentLogService pushGroupContentLogService;
	
	/**
	 * 跳转到群推消息日志视图
	 * @Title: pushGroupContentLogList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("pushgroupcontentlog-list")
	public String pushGroupContentLogList(Model model){
		
		return "msgpush/pushgroupcontentlog-list";
	}
	
	/**
	 * 获取群推消息日志分页信息
	 * @Title: getPushGroupContentLogPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("pushgroupcontentlog-list-data")
	@ResponseBody
	public EasyuiResult<List<PushGroupContentLog>> getPushGroupContentLogPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return pushGroupContentLogService.getPushGroupContentLogPage(map, page, rows);
	}
	/**
	 * 群推消息日志删除
	 * @Title: pushGroupContentLogDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("pushgroupcontentlog-delete")
	@ResponseBody
	public Result pushGroupContentLogDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					pushGroupContentLogService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除群推消息日志成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除群推消息日志失败");
		}
		
	}
	/**
	 * 群推消息日志查看
	 * @Title: pushGroupContentLogInfo
	 * @Description: TODO
	 * @param: @param id
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("pushgroupcontentlog-info")
	public String pushGroupContentLogInfo(@RequestParam(value="id",required=false) Long id,Model model){
		PushGroupContentLog pushGroupContentLog=null;
		if(id!=null){
			pushGroupContentLog=pushGroupContentLogService.get(id);
			if(pushGroupContentLog!=null){
				model.addAttribute("pushGroupContentLog", pushGroupContentLog);
			}
		}
		return "msgpush/pushgroupcontentlog-info";
	}
}
