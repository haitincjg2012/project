package com.alqsoft.service.impl.${lower};

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

import com.alqsoft.dao.${lower}.${bean}Dao;
import com.alqsoft.entity.${lower}.${bean};
import com.alqsoft.service.${lower}.${bean}Service;

/**
 * 
 * @Title: ${bean}ServiceImpl.java
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
public class ${bean}ServiceImpl implements ${bean}Service {
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(${bean}ServiceImpl.class);
	@Autowired
	private ${bean}Dao ${lower}Dao;
	@Override
	@Transactional
	public void delete(Long arg0) {
		${lower}Dao.delete(arg0);
	}

	@Override
	public ${bean} get(Long arg0) {
		
		return ${lower}Dao.findOne(arg0);
	}

	
	@Override
	@Transactional
	public ${bean} saveAndModify(${bean} arg0) {
		return ${lower}Dao.save(arg0);
	}

	
	@Override
	public EasyuiResult<List<${bean}>> get${bean}Page(
			Map<String, Object> map, Integer page, Integer rows) {
		Map<String, SearchFilter> filter = SearchFilter.parse(map);
		Specification<${bean}> specification = DynamicSpecifications.bySearchFilter(filter.values(),
				${bean}.class);
		Page<${bean}> Page = ${lower}Dao.findAll(specification, new PageRequest(page - 1, rows,
				new Sort(Direction.DESC, new String[] { "createdTime" })));
		return EasyUtils.returnPage(${bean}.class, Page);
	}
}
