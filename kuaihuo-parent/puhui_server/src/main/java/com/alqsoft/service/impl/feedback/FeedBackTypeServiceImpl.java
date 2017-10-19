package com.alqsoft.service.impl.feedback;

import java.util.List;
import java.util.Map;

import org.alqframework.collection.MyCollectionUtils;
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

import com.alqsoft.dao.feedback.FeedBackTypeDao;
import com.alqsoft.entity.feedback.FeedBackType;
import com.alqsoft.service.feedback.FeedBackTypeService;

/**
 * 意见反馈类型服务实现类
 * @Title: FeedBackTypeServiceImpl.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:11:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Service
@Transactional
public class FeedBackTypeServiceImpl implements FeedBackTypeService {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(FeedBackServiceImpl.class);
	@Autowired
	private FeedBackTypeDao feedBackTypeDao;
	
	@Override
	public boolean delete(Long arg0) {
		try{
			feedBackTypeDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	@Override
	public FeedBackType get(Long arg0) {
		return feedBackTypeDao.findOne(arg0);
	}
	@Override
	@Transactional
	public EasyuiResult<List<FeedBackType>> getFeedBackTypePage(Map<String, Object> map, Integer page,
			Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<FeedBackType> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				FeedBackType.class);
		Page<FeedBackType> feedBackTypePage=feedBackTypeDao.findAll(specification, new PageRequest(page-1, rows, new Sort(Direction.DESC, new String[]{
		"createdTime"})));
		return EasyUtils.returnPage(FeedBackType.class, feedBackTypePage);
	}
	@Override
	@Transactional
	public FeedBackType saveAndModify(FeedBackType arg0) {
		return feedBackTypeDao.save(arg0);
	}
	@Override
	public List<FeedBackType> findFeedBackTypeAll() {
		return MyCollectionUtils.toList(feedBackTypeDao.findAll());
	}
	@Override
	public FeedBackType getFeedBackTypeByTypeName(String typeName) {
		return feedBackTypeDao.getFeedBackTypeByTypeName(typeName);
	}
}
