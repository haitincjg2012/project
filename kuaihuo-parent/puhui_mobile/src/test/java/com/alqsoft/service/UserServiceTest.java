package com.alqsoft.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.user.User;
import com.alqsoft.service.user.UserService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014-6-6 上午12:19:46
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class UserServiceTest  {
	
	@Autowired
	private UserService userService;
	

	
	@Test
	public void testGetUser() {
		User u = userService.getUser(1L);
		System.out.println(u.getName());
//		u.setName("zd");
//		userService.updateUser(u);
	}

}
