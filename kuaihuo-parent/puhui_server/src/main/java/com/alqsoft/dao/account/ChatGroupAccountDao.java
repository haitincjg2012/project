package com.alqsoft.dao.account;

import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.account.ChatGroupAccount;

/**
 * 
 * @Title: ChatGroupAccountDao.java
 * @Description: 群和用户中间表dao  --消息发送
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:07:38
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ChatGroupAccountDao extends BaseDao<ChatGroupAccount>{
	
	@Query("from ChatGroupAccount cg where cg.accountId=:accountId")
	public List<ChatGroupAccount> findChatGroupAccountByAccountId(@Param("accountId") Long accountId);

	@Query("from ChatGroupAccount cg where cg.chatGroupId=:chatGroupId")
	public List<ChatGroupAccount> findChatGroupAccountByChatGroupId(@Param("chatGroupId") Long chatGroupId);

	@Query("from ChatGroupAccount cg where cg.chatGroupId=:chatGroupId and cg.accountId=:accountId")
	public ChatGroupAccount getChatGroupAccountByChatGroupIdAndAccountId(@Param("chatGroupId") Long chatGroupId,
			@Param("accountId") Long accountId);

	@Modifying
	@Query("DELETE FROM ChatGroupAccount cg WHERE cg.accountId=:accountId")
	public Integer deleteChatGroupAccountByAccountId(@Param("accountId") Long accountId);

	@Modifying
	@Query("DELETE FROM ChatGroupAccount cg WHERE cg.chatGroupId=:chatGroupId")
	public Integer deleteChatGroupAccountByChatGroupId(@Param("chatGroupId") Long chatGroupId);
	
}
