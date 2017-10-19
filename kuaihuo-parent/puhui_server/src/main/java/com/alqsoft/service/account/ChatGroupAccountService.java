package com.alqsoft.service.account;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.account.ChatGroupAccount;

/**
 * 
 * @Title: ChatGroupAccountService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ChatGroupAccountService extends BaseService<ChatGroupAccount>{
	public EasyuiResult<List<ChatGroupAccount>> getChatGroupAccountPage(Map<String, Object> map,Integer page,Integer rows);
	
	
	public List<ChatGroupAccount> findChatGroupAccountByAccountId(Long accountId);

	public List<ChatGroupAccount> findChatGroupAccountByChatGroupId(Long chatGroupId);

	public ChatGroupAccount getChatGroupAccountByChatGroupIdAndAccountId(Long chatGroupId,Long accountId);

	public Integer deleteChatGroupAccountByAccountId(Long accountId);

	public Integer deleteChatGroupAccountByChatGroupId(Long chatGroupId);
}
