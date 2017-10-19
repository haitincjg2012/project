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

import com.alqsoft.dao.sms.ChatGroupToRecordDao;
import com.alqsoft.entity.sms.ChatGroupToRecord;
import com.alqsoft.service.sms.ChatGroupToRecordService;

/**
 * 
 * @Title: ChatGroupToRecordServiceImpl.java
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
public class ChatGroupToRecordServiceImpl implements ChatGroupToRecordService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(ChatGroupToRecordServiceImpl.class);
	@Autowired
	private ChatGroupToRecordDao smsDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			smsDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public ChatGroupToRecord get(Long arg0) {
		
		return smsDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public ChatGroupToRecord saveAndModify(ChatGroupToRecord arg0) {
		return smsDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<ChatGroupToRecord>> getChatGroupToRecordPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<ChatGroupToRecord> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				ChatGroupToRecord.class);
		Page<ChatGroupToRecord> Page = smsDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(ChatGroupToRecord.class, Page);
	}

	@Override
	public ChatGroupToRecord getChatGroupToRecordByUniqueKeyAndAccountId(String uniqueKey,Long accountId) {
		return smsDao.getChatGroupToRecordByUniqueKeyAndAccountId(uniqueKey,accountId);
	}
}
