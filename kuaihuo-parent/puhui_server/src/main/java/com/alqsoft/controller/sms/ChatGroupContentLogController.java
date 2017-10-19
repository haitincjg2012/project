package com.alqsoft.controller.sms;

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

import com.alqsoft.entity.sms.ChatGroupContentLog;
import com.alqsoft.service.sms.ChatGroupContentLogService;

/**
 * 
 * @Title: ChatGroupContentLogAction.java
 * @Description: 群聊群聊消息日志控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月14日 上午9:42:40
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("chatgroupcontentlog")
@Controller
public class ChatGroupContentLogController {
	@Autowired
	private ChatGroupContentLogService chatGroupContentLogService;
	
	/**
	 * 跳转到群聊消息日志视图
	 * @Title: chatGroupContentLogList
	 * @Description: TODO
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("chatgroupcontentlog-list")
	public String chatGroupContentLogList(Model model){
		
		return "sms/chatgroupcontentlog-list";
	}
	
	/**
	 * 获取群聊消息日志分页信息
	 * @Title: getChatGroupContentLogPage
	 * @Description: TODO
	 * @param: @param page
	 * @param: @param rows
	 * @param: @param request
	 * @param: @return
	 * @return: EasyuiResult<List<FeedBack>>
	 * @throws
	 */
	@RequestMapping("chatgroupcontentlog-list-data")
	@ResponseBody
	public EasyuiResult<List<ChatGroupContentLog>> getChatGroupContentLogPage(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows,HttpServletRequest request){
		Map<String, Object> map = ServletUtils.getParametersStartingWith(
				request, "search_");
		return chatGroupContentLogService.getChatGroupContentLogPage(map, page, rows);
	}
	/**
	 * 群聊消息日志删除
	 * @Title: chatGroupContentLogDelete
	 * @Description: TODO
	 * @param: @param id
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("chatgroupcontentlog-delete")
	@ResponseBody
	public Result chatGroupContentLogDelete(@RequestParam("id") String id) {
		String[] ids = StringUtils.split(id, ",");
		try {
				Arrays.stream(ids).forEach(s->{
					chatGroupContentLogService.delete(Long.parseLong(s));	
				});
			return SpringMVCUtils.returnSuccess("删除群聊消息日志成功");
		
		} catch (NumberFormatException e) {
			return SpringMVCUtils.returnError("删除群聊消息日志失败");
		}
		
	}
	/**
	 * 群聊消息日志查看
	 * @Title: chatGroupContentLogInfo
	 * @Description: TODO
	 * @param: @param id
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping("chatgroupcontentlog-info")
	public String chatGroupContentLogInfo(@RequestParam(value="id",required=false) Long id,Model model){
		ChatGroupContentLog chatGroupContentLog=null;
		if(id!=null){
			chatGroupContentLog=chatGroupContentLogService.get(id);
			if(chatGroupContentLog.getId()!=null){
				model.addAttribute("chatGroupContentLog", chatGroupContentLog);
			}
		}
		return "sms/chatgroupcontentlog-info";
	}
}
