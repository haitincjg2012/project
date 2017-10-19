package com.alqsoft.service.impl.account;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.orm.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.account.ChatGroupAccountDao;
import com.alqsoft.entity.account.ChatGroupAccount;
import com.alqsoft.service.account.ChatGroupAccountService;

/**
 * 
 * @Title: ChatGroupAccountServiceImpl.java
 * @Description: 描述
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:48:05 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Service
@Transactional(readOnly=true)
public class ChatGroupAccountServiceImpl implements ChatGroupAccountService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ChatGroupAccountServiceImpl.class);
	@Autowired
	private ChatGroupAccountDao accountDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			accountDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public ChatGroupAccount get(Long arg0) {
		
		return accountDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public ChatGroupAccount saveAndModify(ChatGroupAccount arg0) {
		return accountDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<ChatGroupAccount>> getChatGroupAccountPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroupAccount> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroupAccount.class);
		Page<ChatGroupAccount> Page = accountDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(ChatGroupAccount.class, Page);
	}

	@Override
	public List<ChatGroupAccount> findChatGroupAccountByAccountId(Long accountId) {
		return accountDao.findChatGroupAccountByAccountId(accountId);
	}

	@Override
	public List<ChatGroupAccount> findChatGroupAccountByChatGroupId(Long chatGroupId) {
		return accountDao.findChatGroupAccountByChatGroupId(chatGroupId);
	}

	@Override
	public ChatGroupAccount getChatGroupAccountByChatGroupIdAndAccountId(Long chatGroupId,
			Long accountId) {
		return accountDao.getChatGroupAccountByChatGroupIdAndAccountId(chatGroupId, accountId);
	}

	@Override
	public Integer deleteChatGroupAccountByAccountId(Long accountId) {
		return accountDao.deleteChatGroupAccountByAccountId(accountId);
	}

	@Override
	public Integer deleteChatGroupAccountByChatGroupId(Long chatGroupId) {
		return accountDao.deleteChatGroupAccountByChatGroupId(chatGroupId);
	}
}
