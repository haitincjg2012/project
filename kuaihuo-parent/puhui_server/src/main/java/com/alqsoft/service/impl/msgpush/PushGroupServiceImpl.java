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

import com.alqsoft.dao.msgpush.PushGroupDao;
import com.alqsoft.entity.msgpush.PushGroup;
import com.alqsoft.service.msgpush.PushGroupService;
import com.alqsoft.utils.easyuiweb.EasyWebUtils;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushGroupServiceImpl.java
 * @Description: 群推送实体
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
public class PushGroupServiceImpl implements PushGroupService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(PushGroupServiceImpl.class);
	@Autowired
	private PushGroupDao msgpushDao;
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
	public PushGroup get(Long arg0) {
		
		return msgpushDao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public PushGroup saveAndModify(PushGroup arg0) {
		return msgpushDao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<PushGroup>> getPushGroupPage(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroup> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroup.class);
		Page<PushGroup> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(PushGroup.class, Page);
	}

	@Override
	public PushGroup getPushGroupByPushGroupName(String pushGroupName) {
		return msgpushDao.getPushGroupByPushGroupName(pushGroupName);
	}
	
	@Override
	public EasyuiWebResult<List<PushGroup>> getPushGroupPageByMobile(Map<String, Object> map,
			Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<PushGroup> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				PushGroup.class);
		Page<PushGroup> Page = msgpushDao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyWebUtils.returnPage(PushGroup.class, Page);
	}
}
