package com.alqsoft.service.impl.infotemplate;

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

import com.alqsoft.dao.infotemplate.InfoTypeDao;
import com.alqsoft.entity.infotemplate.InfoType;
import com.alqsoft.service.infotemplate.InfoTypeService;

/**
 * 
 * @Title: InfoTypeServiceImpl.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午5:42:58
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Service
@Transactional(readOnly = true)
public class InfoTypeServiceImpl implements InfoTypeService {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(InfoTypeServiceImpl.class);
	@Autowired
	private InfoTypeDao infoTypeDao;
	
	@Override
	@Transactional
	public boolean delete(Long arg0) {
		try{
			infoTypeDao.delete(arg0);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	@Override
	public InfoType get(Long arg0) {
		return infoTypeDao.findOne(arg0);
	}

	@Override
	@Transactional
	public InfoType saveAndModify(InfoType arg0) {
		return infoTypeDao.save(arg0);
	}

	@Override
	public EasyuiResult<List<InfoType>> getInfoTypePage(Map<String, Object> map, Integer page,
			Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<InfoType> specification = DynamicSpecifications.bySearchFilter(
				filter.values(), InfoType.class);
		Page<InfoType> infoTypePage = infoTypeDao.findAll(specification, new PageRequest(
				page - 1, rows, new Sort(Direction.DESC, new String[] { "updateTime" })));
		return EasyUtils.returnPage(InfoType.class, infoTypePage);
	}
	
	@Override
	public InfoType getInfoTypeByTypeEnglishName(String typeEnglishName) {
		return infoTypeDao.getInfoTypeByTypeEnglishName(typeEnglishName);
	}
	
}
