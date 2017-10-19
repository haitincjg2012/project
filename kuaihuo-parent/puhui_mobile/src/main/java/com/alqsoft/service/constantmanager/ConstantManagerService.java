package com.alqsoft.service.constantmanager;

import java.util.List;

import com.alqsoft.entity.constantmanager.ConstantManager;

/**
 * @Title: ConstantManagerService.java
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月26日 下午3:07:59
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ConstantManagerService {

	/**
	 * 根据常量查询常驻内存的常量数据
	 * 
	 * @param isMemory
	 * @return
	 */
	public List<ConstantManager> findConstantManagerByIsMemory(Integer isMemory);

}
