package com.alqsoft.mongo.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.user.User;
import com.alqsoft.mongo.entity.MongoUser;
import com.alqsoft.service.user.UserService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月20日 上午1:23:47
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class MongoUserServiceTest {
	
	@Autowired
	private MongoUserService mongoUserService;
	
	@Autowired
	private UserService userService;

	@Test
	public void testsave() throws NoSuchMethodException, SecurityException {
		List<User> users=userService.getUsersByRoleId(2l);
		for (User user : users) {
			MongoUser mongoUser=new MongoUser();
			mongoUser.setId(user.getId());
			mongoUser.setAddress(user.getAddress());
			mongoUserService.saveAndModify(mongoUser);
		}
	}
	
	
	@Test
	public void tesget() {
		MongoUser mongoUser=mongoUserService.get(17l);
		System.out.println(mongoUser.getAddress());
	}
	
	@Test
	public void testPage(){
		Page<MongoUser> pages = mongoUserService.getPage();
		for (MongoUser mongoUser : pages.getContent()) {
			System.out.println(mongoUser.getId());
		}
	}
	@Test
	public void testFindOne(){
		MongoUser mongoUser = mongoUserService.findOne(18l, "53535");
		System.out.println("********"+mongoUser);
	}
}
