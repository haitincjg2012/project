package com.alqsoft.mybatis.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.mybatis.entity.user.MyUser;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015s
 * @create-time 2014-4-25 上午1:30:55
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private MyUserService myUserService;
	
	@Test
	public void testGetUser() {
		MyUser u = myUserService.getUser(1L);
		System.out.println("***************----"+u.getName());
		u.setName("zdadfadfafadfadfadfadf");
		myUserService.updateUser(u);
	}
}
