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

import com.alqsoft.dao.sms.SmsContentLogDao;
import com.alqsoft.entity.sms.SmsContentLog;
import com.alqsoft.service.sms.SmsContentLogService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: SmsContentLogServiceImpl.java
 * @Description: 消息聊天日志 实现类
 * @author 作者 
 * @e-mail 
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月22日 下午2:48:05 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@Service
@Transactional(readOnly=true)
public class SmsContentLogServiceImpl implements SmsContentLogService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(SmsContentLogServiceImpl.class);
	@Autowired
	private SmsContentLogDao smsDao;
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
	public SmsContentLog get(Long arg0) {
		
		return smsDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public SmsContentLog saveAndModify(SmsContentLog arg0) {
		return smsDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<SmsContentLog>> getSmsContentLogPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<SmsContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				SmsContentLog.class);
		Page<SmsContentLog> Page = smsDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(SmsContentLog.class, Page);
	}
	
	/**
	 * 通过用户唯一id获取单发消息日志
	 */
	@Override
	public SmsContentLog getSmsContentLogByUniqueKey(String uniqueKey) {
		return smsDao.getSmsContentLogByUniqueKey(uniqueKey);
	}
	
	@Override
	public EasyuiWebResult<List<SmsContentLog>> getSmsContentLogPageByMobile(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<SmsContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				SmsContentLog.class);
		Page<SmsContentLog> Page = smsDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(SmsContentLog.class, Page);
	}
}
