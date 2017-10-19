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

import com.alqsoft.entity.msgpush.PushContentLog;
import com.alqsoft.service.msgpush.PushContentLogService;

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
@RequestMapping("pushcontentlog")
@Controller
public class PushContentLogController {
	@Autowired
	private PushContentLogService pushContentLogService;
	
	/**
	 * 跳转到单推消息日志视图
	 * @Title: pushContentLogList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("pushcontentlog-list")
	public String pushContentLogList(Model model){
		
		return "msgpush/pushcontentlog-list";
	}
	
	/**
	 * 获取单推消息日志分页信息
	 * @Title: getPushContentLogPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("pushcontentlog-list-data")
	@ResponseBody
	public EasyuiResult<List<PushContentLog>> getPushContentLogPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return pushContentLogService.getPushContentLogPage(map, page, rows);
	}
	/**
	 * 单推消息日志删除
	 * @Title: pushContentLogDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("pushcontentlog-delete")
	@ResponseBody
	public Result pushContentLogDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					pushContentLogService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除单推消息日志成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除单推消息日志失败");
		}
		
	}
	/**
	 * 单推消息日志查看
	 * @Title: pushContentLogInfo
	 * @Description: TODO
	 * @param: @param id
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("pushcontentlog-info")
	public String pushContentLogInfo(@RequestParam(value="id",required=false) Long id,Model model){
		PushContentLog pushContentLog=null;
		if(id!=null){
			pushContentLog=pushContentLogService.get(id);
			if(pushContentLog!=null){
				model.addAttribute("pushContentLog", pushContentLog);
			}
		}
		return "msgpush/pushcontentlog-info";
	}
}
