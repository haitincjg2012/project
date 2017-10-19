package com.alqsoft.service.impl.user;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-29 上午10:32:00
 * 
 */
public class UserServiceImplTest {
	

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAndModify() {
		StandardPasswordEncoder passwordEncoder=new StandardPasswordEncoder("shoudan");
		System.out.println(passwordEncoder.encode("123456"));
	}

	@Test
	public void testGetUserByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsersByRoleId() {
		fail("Not yet implemented");
	}

}
