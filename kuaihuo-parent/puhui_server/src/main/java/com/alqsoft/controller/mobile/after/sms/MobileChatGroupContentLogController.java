package com.alqsoft.controller.mobile.after.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.sms.ChatGroupContentLog;
import com.alqsoft.service.sms.ChatGroupContentLogService;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: MobileChatGroupController.java
 * @Description: 客户端群操作控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月18日 上午9:42:27
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("view/mobile/after/chatgroupcontentlog")
@Controller
public class MobileChatGroupContentLogController {
	@Autowired
	private ChatGroupContentLogService chatGroupContentLogService;
	/**
	 * 群聊消息查看 通过发送者id或接收者id查看
	 * @Title: FindChatGroupContentLog
	 * @Description: TODO
	 * @param: @param pageNo
	 * @param: @param pageSize
	 * @param: @param id
	 * @param: @param type
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping(value="findChatGroupContentLog",method=RequestMethod.POST)
	@ResponseBody
	public Result FindChatGroupContentLog(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,
			@RequestParam("smsSenderId") Long smsSenderId,@RequestParam("smsReceiveId") Long smsReceiveId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_smsSenderId", smsSenderId);
		map.put("EQ_smsReceiveId", smsReceiveId);
		EasyuiWebResult<List<ChatGroupContentLog>> list = chatGroupContentLogService.getChatGroupContentLogPageByMobile(map, pageNo, pageSize);
		if(list.getTotal().longValue()==0)
		{
			return ResultUtils.returnError("列表不存在",2);
		}
		return ResultUtils.returnSuccess("获取列表成功",list);
	}
	/**
	 * 群聊消息查看 通过发送者id或接收者id 时间查看
	 * @Title: FindChatGroupContentLogByTime
	 * @Description: TODO
	 * @param: @param pageNo
	 * @param: @param pageSize
	 * @param: @param id
	 * @param: @param type
	 * @param: @param time
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping(value="findChatGroupContentLogByTime",method=RequestMethod.POST)
	@ResponseBody
	public Result FindChatGroupContentLogByTime(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,
			@RequestParam("smsSenderId") Long smsSenderId,@RequestParam("smsReceiveId") Long smsReceiveId,@RequestParam("year") Integer year,
			@RequestParam("month") Integer month,@RequestParam("day") Integer day)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_smsSenderId", smsSenderId);
		map.put("EQ_smsReceiveId", smsReceiveId);
		map.put("EQ_year", year);
		map.put("EQ_month", month);
		map.put("EQ_day", day);
		EasyuiWebResult<List<ChatGroupContentLog>> list = chatGroupContentLogService.getChatGroupContentLogPageByMobile(map, pageNo, pageSize);
		if(list.getTotal().longValue()==0)
		{
			return ResultUtils.returnError("列表不存在",2);
		}
		return ResultUtils.returnSuccess("获取列表成功",list);
	}
}
