package com.alqsoft.service.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.user.User;
import com.alqsoft.service.icon.IconService;
import com.alqsoft.service.resource.ResourceService;
import com.alqsoft.service.role.RoleService;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-6-6 下午10:58:40
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private IconService iconService;
	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private XStreamMarshaller xStreamMarshaller;
	
	@Test
	public void test() throws XmlMappingException, IOException{
		User u = userService.get(1l);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(stream);
		xStreamMarshaller.marshal(u, result);
		System.out.println(new String(stream.toByteArray()));
	}
}
