package com.alqsoft.webservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.service.log.UserLogService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-5-25 下午6:46:03
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class HelloServiceTest {
	
	@Resource
	private WsHelloService helloService;
	
	
	@Autowired
	private UserLogService userLogService2;

	@Test
	public void testGetUserLogByState() {
		System.out.println(helloService.sayHi("我是好人"));
	}

	
	@Test
	public void testGetUserLdfsdfe() {
		System.out.println(userLogService2.getUserLogByState());
	}
}
