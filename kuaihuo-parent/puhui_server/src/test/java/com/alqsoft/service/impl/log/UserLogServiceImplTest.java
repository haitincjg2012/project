package com.alqsoft.service.impl.log;

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
 * @create-time 2014-5-30 下午2:11:20
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class UserLogServiceImplTest {
	
	@Autowired
	private UserLogService userLogService;

	@Test
	public void testGetUserLogByState() {
		userLogService.getUserLogByState();
	}

}
