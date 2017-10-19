package com.alqsoft.service.impl.constantmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.constantmanager.ConstantManagerDao;
import com.alqsoft.entity.constantmanager.ConstantManager;
import com.alqsoft.service.constantmanager.ConstantManagerService;

/**
 * @Title: ConstantManagerServiceImpl.java
 * @Description: 常量管理实现类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月26日 下午3:08:59
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@Service
@Transactional(readOnly=true)
public class ConstantManagerServiceImpl implements ConstantManagerService{
	
	@Autowired
	private ConstantManagerDao constantManagerDao;

	@Override
	public List<ConstantManager> findConstantManagerByIsMemory(Integer isMemory) {
		return constantManagerDao.findConstantManagerByIsMemory(isMemory);
	}

}
