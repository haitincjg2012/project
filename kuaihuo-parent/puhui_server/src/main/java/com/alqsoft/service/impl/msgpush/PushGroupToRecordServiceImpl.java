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

import com.alqsoft.dao.msgpush.PushGroupToRecordDao;
import com.alqsoft.entity.msgpush.PushGroupToRecord;
import com.alqsoft.service.msgpush.PushGroupToRecordService;

/**
 * 
 * @Title: PushGroupToRecordServiceImpl.java
 * @Description: 群推送到记录表实现类
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
public class PushGroupToRecordServiceImpl implements PushGroupToRecordService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(PushGroupToRecordServiceImpl.class);
	@Autowired
	private PushGroupToRecordDao msgpushDao;
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
	public PushGroupToRecord get(Long arg0) {
		
		return msgpushDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public PushGroupToRecord saveAndModify(PushGroupToRecord arg0) {
		return msgpushDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<PushGroupToRecord>> getPushGroupToRecordPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroupToRecord> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroupToRecord.class);
		Page<PushGroupToRecord> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(PushGroupToRecord.class, Page);
	}

	@Override
	public PushGroupToRecord getPushGroupToRecordByUniqueKeyAndAccountId(String uniqueKey,
			Long accountId) {
		return msgpushDao.getPushGroupToRecordByUniqueKeyAndAccountId(uniqueKey, accountId);
	}
}
