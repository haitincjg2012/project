package com.alqsoft.controller.mobile.after.sms;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.account.ChatGroupAccount;
import com.alqsoft.service.account.ChatGroupAccountService;

/**
 * 
 * @Title: MobileChatGroupController.java
 * @Description: 客户端群成员中间操作控制器
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月18日 上午9:42:27
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RequestMapping("view/mobile/after/chatgroupaccount")
@Controller
public class MobileChatGroupAccountController {
	@Autowired
	private ChatGroupAccountService chatGroupAccountService;
	/**
	 * 将用户加入群
	 * @Title: addChatgroupAccount
	 * @Description: TODO
	 * @param: @param accountId
	 * @param: @param accountName
	 * @param: @param chatGroupId
	 * @param: @return
	 * @return: Result
	 * @throws
	 */
	@RequestMapping("addChatgroupAccount")
	@ResponseBody
	public Result addChatgroupAccount(@RequestParam("accountId") Long accountId,@RequestParam("accountName") String accountName,
			@RequestParam("chatGroupId") Long chatGroupId)
	{
		ChatGroupAccount chatGroupAccount = new ChatGroupAccount();
		chatGroupAccount.setAccountId(accountId);
		chatGroupAccount.setAccountName(accountName);
		chatGroupAccount.setChatGroupId(chatGroupId);
		ChatGroupAccount chatGroupAccount2 = chatGroupAccountService.saveAndModify(chatGroupAccount);
		if(chatGroupAccount2!=null)
		{
			return ResultUtils.returnSuccess("加入群成功", chatGroupAccount2);
		}
		else
		{
			return ResultUtils.returnError("加入群失败", 0);
		}
	}
}
