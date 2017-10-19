package com.alqsoft.service.impl.msgpush;

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

import com.alqsoft.dao.msgpush.PushGroupContentLogDao;
import com.alqsoft.entity.msgpush.PushGroupContentLog;
import com.alqsoft.service.msgpush.PushGroupContentLogService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushGroupContentLogServiceImpl.java
 * @Description: 消息群推日志
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
public class PushGroupContentLogServiceImpl implements PushGroupContentLogService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(PushGroupContentLogServiceImpl.class);
	@Autowired
	private PushGroupContentLogDao msgpushDao;
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			msgpushDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public PushGroupContentLog get(Long arg0) {
		
		return msgpushDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public PushGroupContentLog saveAndModify(PushGroupContentLog arg0) {
		return msgpushDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<PushGroupContentLog>> getPushGroupContentLogPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroupContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroupContentLog.class);
		Page<PushGroupContentLog> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(PushGroupContentLog.class, Page);
	}

	@Override
	public PushGroupContentLog getPushGroupContentLogByUniqueKey(String uniqueKey) {
		return msgpushDao.getPushGroupContentLogByUniqueKey(uniqueKey);
	}
	@Override
	public EasyuiWebResult<List<PushGroupContentLog>> getPushGroupContentLogPageByMobile(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroupContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroupContentLog.class);
		Page<PushGroupContentLog> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(PushGroupContentLog.class, Page);
	}
}
