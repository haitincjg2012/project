package com.alqsoft.service.impl.sms;

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

import com.alqsoft.dao.sms.ChatGroupDao;
import com.alqsoft.entity.sms.ChatGroup;
import com.alqsoft.service.sms.ChatGroupService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: ChatGroupServiceImpl.java
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
public class ChatGroupServiceImpl implements ChatGroupService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ChatGroupServiceImpl.class);
	@Autowired
	private ChatGroupDao chatGroupDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			chatGroupDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public ChatGroup get(Long arg0) {
		
		return chatGroupDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public ChatGroup saveAndModify(ChatGroup arg0) {
		return chatGroupDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<ChatGroup>> getChatGroupPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroup> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroup.class);
		Page<ChatGroup> Page = chatGroupDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(ChatGroup.class, Page);
	}
	
	@Override
	public ChatGroup getChatGroupByChatGroupName(String chatGroupName) {
		return chatGroupDao.getChatGroupByChatGroupName(chatGroupName);
	}
	
	@Override
	public EasyuiWebResult<List<ChatGroup>> getChatGroupPageByMobile(Map<String, Object> map,
			Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroup> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroup.class);
		Page<ChatGroup> Page = chatGroupDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(ChatGroup.class, Page);
	}
}
