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

import com.alqsoft.dao.msgpush.PushContentLogDao;
import com.alqsoft.entity.msgpush.PushContentLog;
import com.alqsoft.service.msgpush.PushContentLogService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushContentLogServiceImpl.java
 * @Description: 消息单推日志实现类
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
public class PushContentLogServiceImpl implements PushContentLogService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(PushContentLogServiceImpl.class);
	@Autowired
	private PushContentLogDao msgpushDao;
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
	public PushContentLog get(Long arg0) {
		
		return msgpushDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public PushContentLog saveAndModify(PushContentLog arg0) {
		return msgpushDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<PushContentLog>> getPushContentLogPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushContentLog.class);
		Page<PushContentLog> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(PushContentLog.class, Page);
	}

	@Override
	public PushContentLog getPushContentLogByUniqueKey(String uniqueKey) {
		return msgpushDao.getPushContentLogByUniqueKey(uniqueKey);
	}
	@Override
	public EasyuiWebResult<List<PushContentLog>> getPushContentLogPageByMobile(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushContentLog> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushContentLog.class);
		Page<PushContentLog> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(PushContentLog.class, Page);
	}
}
