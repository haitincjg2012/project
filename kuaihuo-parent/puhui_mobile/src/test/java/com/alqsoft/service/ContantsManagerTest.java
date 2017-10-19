package com.alqsoft.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.constantmanager.ConstantManager;
import com.alqsoft.service.constantmanager.ConstantManagerService;

/**
 * @Title: ContantsManagerTest.java
 * @Description: 常量测试类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月26日 下午3:11:24
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class ContantsManagerTest {

	@Autowired
	private ConstantManagerService  constantManagerService;
	
	@Test
	public void test() {
		
		List<ConstantManager> lists=constantManagerService.findConstantManagerByIsMemory(1);
		
		System.out.println("条数==："+lists.size());
	}

}
