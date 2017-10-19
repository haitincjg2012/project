package com.alqsoft.controller.mobile.after.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.sms.ChatGroup;
import com.alqsoft.service.sms.ChatGroupService;
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
@RequestMapping("view/mobile/after/chatgroup")
@Controller
public class MobileChatGroupController {
	@Autowired
	private ChatGroupService chatGroupService;
	/**
	 * 创建群操作
	 * @Title: addChatgroup
	 * @Description: TODO
	 * @param: @param chatGroupName
	 * @param: @param accountNum
	 * @param: @param description
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("addChatgroup")
	@ResponseBody
	public Result addChatgroups(@RequestParam("chatGroupName") String chatGroupName,@RequestParam("accountNum") Long accountNum,String description)
	{
		if(chatGroupService.getChatGroupByChatGroupName(chatGroupName)!=null)
		{
			return ResultUtils.returnError("该群名已被使用，请重新输入。", 0);
		}
		ChatGroup chatGroup = new ChatGroup();
		chatGroup.setAccountNum(accountNum);
		chatGroup.setChatGroupName(chatGroupName);
		chatGroup.setDescription(description);
		ChatGroup chatGroup2 = chatGroupService.saveAndModify(chatGroup);
		if(chatGroup2!=null)
		{
			return ResultUtils.returnSuccess("群添加成功", chatGroup2);
		}
		else
		{
			return ResultUtils.returnError("群添加失败", 0);
		}
	}
	/**
	 * 返回群列表接口
	 * @Title: findChatGroup
	 * @Description: TODO
	 * @param: @param pageNo
	 * @param: @param pageSize
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("findChatGroup")
	@ResponseBody
	public Result findChatGroup(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		EasyuiWebResult<List<ChatGroup>> list = chatGroupService.getChatGroupPageByMobile(map, pageNo, pageSize);
		if(list.getTotal()<=0)
		{
			return ResultUtils.returnError("列表不存在", -2);
		}
		return ResultUtils.returnSuccess("获取列表成功", list);
	}
}
